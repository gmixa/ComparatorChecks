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
private class Scanner(packageName: String) extends Scanning(packageName) with LazyLogging :

  def allComparable(): Try[Set[Class[_ <: Comparable[_]]]] = using {
    scanResult =>
      val classes = scanResult.getClassesImplementing(classOf[Comparable[_]])
      val clazz = classes.loadClasses(classOf[Comparable[_]])
      clazz.asScala.toSet
  }

  def allComparators(): Try[Set[Class[_ <: Comparator[_]]]] = using {
    scanResult =>
      val classes = scanResult.getClassesImplementing(classOf[Comparator[_]])
      val clazz = classes.loadClasses(classOf[Comparator[_]])
      clazz.asScala.toSet
  }

  def allFieldsDefiningComparators(): Try[Set[java.lang.reflect.Field]] = using {
    scanResult =>
      val fields: List[Option[FieldInfo]] = for {
        classes <- scanResult.getAllClasses.asScala.toList
        field <- classes.getFieldInfo.asScala.toList if checkForClassRefType(field.getTypeSignatureOrTypeDescriptor)
      } yield {
        extractComparators(field)
      }
      println(fields)
      fields.filter( p => p.isDefined).map(p => p.get.loadClassAndGetField()).toSet
  }

  private final def extractComparators(info: FieldInfo) : Option[FieldInfo] =
    if (info.getClassInfo.implementsInterface(classOf[Comparator[_]])) {
      Some(info)
    } else {
      None
    }
  end extractComparators


  private final def checkForClassRefType(fieldTypeSignature: TypeSignature) : Boolean =
    fieldTypeSignature match
      case _: ClassRefTypeSignature => true
      case _ => false
    end match
  end checkForClassRefType

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

  private final def extractRelevantLambdaMethods(info: MethodInfo,formalParametersAreComparable : Boolean) : Option[MethodInfo] =
    info match
      case methodInfo
        if methodInfo.isSynthetic &&
          methodInfo.isPrivate && methodInfo.isStatic && hasSignature(methodInfo,formalParametersAreComparable) => Some(methodInfo)
      case _ => None
    end match
  end extractRelevantLambdaMethods

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
    val parametersHaveSameType = classNameOfSecondParameter == classNameOfFirstParameter
    if !formalParametersAreComparable then
      isIntReturned && hasTwoParameter && parametersHaveSameType
    else
      isIntReturned && hasTwoParameter && parametersHaveSameType && implementsComparable(first)
    end if
  end hasSignature


  private def implementsComparable( methodInfo: Option[MethodParameterInfo]) : Boolean =
    methodInfo match
      case Some(value) => implementsComparable(value.getTypeSignature)
      case None => false
    end match
  end implementsComparable


  private def implementsComparable(signature: TypeSignature): Boolean =
    signature match
      case sig: ClassRefTypeSignature =>
        /**
         * ClassGraph does NOT scan internals of the jdk. Thats why we have to list all internal
         * classes that we take into regard for evaluation. The classes here are all supposed to be comparable
         * and end up as our base comparable elements. all non base non compareable classes are either sum or product classes
         * built by recursive combination
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

object Scanner:
  def apply(packageName: String): Scanner =
    require(packageName != null, "null no legal package name")
    new Scanner(packageName)
  end apply
end Scanner

