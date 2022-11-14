package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.*

import java.lang.reflect.{ParameterizedType, Type}
import java.util.Comparator
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}

private class ComparatorInspector(packageName : String) extends Scanning(packageName):
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

object ComparatorInspector:
  def apply(packageName : String) :ComparatorInspector =
    new ComparatorInspector(packageName)
  end apply
end ComparatorInspector
