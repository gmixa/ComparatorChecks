package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.*

import java.lang.Comparable
import java.lang.reflect.{ParameterizedType, Type, Field as JField}
import java.util
import java.util.Comparator
import scala.annotation.static
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}
import com.typesafe.scalalogging.LazyLogging
import cats.*
import cats.syntax.all.*

/**
 * a concrete scanner that is used to look up different classes that have
 * some specific 'features' that we want to investigate further.
 *
 * @param packageName the package that is to be evaluated
 */
private class Scanner(packageName: String) extends Scanning(packageName) with LazyLogging :

  /**
   * determines all classes that implement the comparable interface
   * @return Failure(exception) if there where technical problems while evaluating.
   *         Success(foundClasses) all found comparable wrapped in a Success Object.
   *         foundClasses can be empty if none were found and ther was no technical problem
   */
  def allComparable(): Try[Set[Class[_ <: Comparable[_]]]] = using {
    scanResult =>
      val classes = scanResult.getClassesImplementing(classOf[Comparable[_]])
      val clazz = classes.loadClasses(classOf[Comparable[_]])
      clazz.asScala.toSet
  }

  /**
   * determines all classes that implement the Comparator type
   * @return Try wrapping the set of found results.
   * @see [[allComparable]] for detailed return semantics
   */
  def allComparators(): Try[Set[Class[_ <: Comparator[_]]]] = using {
    scanResult =>
      val classes = scanResult.getClassesImplementing(classOf[Comparator[_]])
      val clazz = classes.loadClasses(classOf[Comparator[_]])
      clazz.asScala.toSet
  }

  /**
   * all fields of classes within the introspected class that refer to a
   * Comparator. These comparators are of interest for further evaluation.
   * Typical Scenario a class that "collects" different comparators for different
   * desired sorts.
   * @return Set of found fields that classes do contain and refer to a type implementing
   *         Comparator interface
   * @see [[allComparable]] for detailed return semantics
   */
  def allFieldsDefiningComparators(): Try[Set[java.lang.reflect.Field]] = using {
    scanResult =>
      val fields: List[Option[FieldInfo]] = for {
        classes <- scanResult.getAllClasses.asScala.toList
        field <- classes.getFieldInfo.asScala.toList if checkForClassRefType(field.getTypeSignatureOrTypeDescriptor)
      } yield {
        extractComparators(field)
      }
      fields.filter( p => p.isDefined).map(p => p.get.loadClassAndGetField()).toSet
  }

  /**
   * checks if a field is a type implementing the Comparator interface
   * @param info field to check
   * @return Some(field) when it is a Comparator else None
   */
  private final def extractComparators(info: FieldInfo) : Option[FieldInfo] =
    if info.getClassInfo.implementsInterface(classOf[Comparator[_]]) then
      Some(info)
    else
      None
  end extractComparators


  /**
   * checks if a field references some class object or is something else
   * @param fieldTypeSignature signature of a class field
   * @return true if the field canrefers to a classreference false else
   */
  private final def checkForClassRefType(fieldTypeSignature: TypeSignature) : Boolean =
    fieldTypeSignature match
      case _: ClassRefTypeSignature => true
      case _ => false
    end match
  end checkForClassRefType

  /**
   * since java 8 a lambda also can define a comparator. lambdas have a special low level
   * representation that make them no subclass of Comparator interface even if they "implement"
   * it. That is why we have to use a different approach to detect these lambdas
   * @param formalParametersAreComparable the lambda parameters are Compareable or not
   * @return all lambdas that we expect to be a Comparable implementation.
   */
  def allLambdasDefiningAComparator(formalParametersAreComparable : Boolean = false ) : Try[Set[MethodInfo]] = using{
    scanResult =>
      val suspiciousMethods : List[Option[MethodInfo]] = for {
        classes <- scanResult.getAllClasses.asScala.toList
        methods <- classes.getMethodInfo.asScala.toList
      } yield {
        extractRelevantLambdaMethods(methods,formalParametersAreComparable)
      }
      suspiciousMethods.filter(_.isDefined).map(_.get).toSet
  }

  /**
   * criteria to test if a given method is a lambda method that can be seen as an "implementation"
   * of the Comparator interface
   * @param info method to be tested
   * @param formalParametersAreComparable if we want the parameter of the lambda be itself comparable.
   * @return Some(info) if tests are passed else None
   */
  private final def extractRelevantLambdaMethods(info: MethodInfo,formalParametersAreComparable : Boolean) : Option[MethodInfo] =
    info match
      case methodInfo
        if methodInfo.isSynthetic &&
          methodInfo.isPrivate && methodInfo.isStatic && hasSignature(methodInfo,formalParametersAreComparable) => Some(methodInfo)
      case _ => None
    end match
  end extractRelevantLambdaMethods

  /**
   * checks if a signature of a method could be seen as an implementation of a Comparator
   * @param info method to be tested
   * @param formalParametersAreComparable if formal parameters have to be comparable
   * @return true if we "think" we found a lambda comparator
   */
  private final def hasSignature(info: MethodInfo,formalParametersAreComparable : Boolean) : Boolean =
    val typeSignature: MethodTypeSignature = info.getTypeSignatureOrTypeDescriptor
    val isIntReturned = typeSignature.getResultType match
      case signature: BaseTypeSignature => signature.getTypeStr == "int"
      case _ => false
    val parameterInfo = info.getParameterInfo.toList
    val hasTwoParameter = parameterInfo.size == 2
    val (first,second) = (parameterInfo.headOption,parameterInfo.tail.headOption)
    val classNameOfFirstParameter = className(first)
    val classNameOfSecondParameter = className(second)
    val parametersHaveSameType = classNameOfSecondParameter === classNameOfFirstParameter
    if !formalParametersAreComparable then
      isIntReturned && hasTwoParameter && parametersHaveSameType
    else
      isIntReturned && hasTwoParameter && parametersHaveSameType && implementsComparable(first)
    end if
  end hasSignature


  /**
   * checks of a formal an optional existing formal parameter of a method is itself comparable
   * @param methodParameterInfo parameter of a method that has to be evaluated further if it exists
   * @return true if we have an existing formal parameter that is comparable else false
   */
  private def implementsComparable(methodParameterInfo: Option[MethodParameterInfo]) : Boolean =
    methodParameterInfo match
      case Some(value) => implementsComparable(value.getTypeSignatureOrTypeDescriptor)
      case None => false
    end match
  end implementsComparable


  /**
   * checks if we have a formal parameter that is comparable
   * @param signature of a formal parameter
   * @return true if comparable else false
   */
  private def implementsComparable(signature: TypeSignature): Boolean =
    signature match
      case sig: ClassRefTypeSignature =>
        /**
         * ClassGraph does NOT scan internals of the jdk. That is why we have to list all internal
         * classes that we take into regard for evaluation. The classes here are all supposed to be comparable
         * and end up as our base comparable elements. all non base non comparable classes are either sum or product classes
         * built by recursive combination.
         * As a consequence of the ClassGraph behaviour we have to list all jdk classes that are
         * Comparable explicitly.
         */
        sig.getFullyQualifiedClassName match
          case "java.lang.Integer" => true
          case "java.lang.String" => true
          case "java.lang.Long" => true
          case "java.lang.Short" => true
          case "java.lang.Byte" => true
          case "java.lang.Character" => true
          case _ => sig.getClassInfo.implementsInterface(classOf[Comparable[_]])
        end match
      case _ => false // jvm primitives can be compared but do not implement Comparable
    end match
  end implementsComparable

  /**
   * determines the unique name of a method parameter if possible
   * @param info information about a method parameter
   * @return name that describes the parameters type
   */
  private def className(info: Option[MethodParameterInfo]): Option[String] =
    info match
      case None => None
      case Some(info) =>
        info.getTypeSignatureOrTypeDescriptor match
          case signature: BaseTypeSignature => Option(signature.getTypeStr)
          case signature: ReferenceTypeSignature =>
            signature match
              case sig: ArrayTypeSignature => Option(sig.getTypeSignatureStr)
              case sig: ClassRefOrTypeVariableSignature =>
                sig match
                  case s: ClassRefTypeSignature => Option(s.getFullyQualifiedClassName)
                  case s: TypeVariableSignature => Option(s.getName)
    end match
  end className

/**
 * Factory for creating a Scanner
 */
object Scanner:

  /**
   * Creates a scanner for a specific package
   * @param packageName the name of the package that will be analyzed
   * @return scanner for further use
   */
  def apply(packageName: String): Scanner =
    require(packageName != null, "null no legal package name")
    new Scanner(packageName)
  end apply
end Scanner

