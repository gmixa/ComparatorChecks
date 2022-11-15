package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.*

import java.lang.reflect.{ParameterizedType, Type}
import java.util.Comparator
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}

/**
 * inspects a concrete class within a given package for the type of comparator that it is implementing
 * @param packageName the package that contains all the classes that have to be evaluated
 */
private class ComparatorInspector(packageName : String) extends Scanning(packageName):

  /**
   * Tries to determine the formal type parameter of a concrete Comparator
   * @param clazz the java class which we try to get the information of
   * @tparam T a class that implements the Comparator Interface
   * @return A try containing the result of the class inspection.
   *         Failure if some unexpected problem occurred
   *         Success if there were no obvious technical problems
   *            Success(Type) if we could determine some type successfully
   *            None no technical issues but no success retrieving useful information
   *
   */
  def comparatorType[T <: Comparator[_ <: Any]](clazz: Class[T]): Try[Option[Type]] = using {
    scanResult =>
      val comparatorClass = scanResult.getClassInfo(clazz.getName)
      val typeArgument = comparatorClass.getTypeSignatureOrTypeDescriptor.getSuperinterfaceSignatures.get(0).getTypeArguments.get(0)
      val typeSignature = typeArgument.getTypeSignature
      val clazzName = typeSignature match
        case signature: ClassRefTypeSignature => signature.loadClass()
        case _ => null
      Some(clazzName)
  }
end ComparatorInspector

/**
 * Factory to create a comparator Inspector
 */
object ComparatorInspector:
  /**
   * returns a concrete implementation to inspect classes about te type of comparator
   * which they do implement
   * @param packageName package that is used to evaluate
   * @return concrete implementation of  a comparator inspector
   */
  def apply(packageName : String) :ComparatorInspector =
    new ComparatorInspector(packageName)
  end apply
end ComparatorInspector
