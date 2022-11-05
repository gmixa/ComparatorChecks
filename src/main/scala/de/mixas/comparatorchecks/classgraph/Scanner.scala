package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.{ClassGraph, ClassInfoList, ScanResult}

import java.lang.Comparable
import java.lang.reflect.{Field, ParameterizedType, Type}
import java.util
import java.util.Comparator
import scala.annotation.static
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}

private class Scanner(packageName : String):

  private final def using[T]: (ScanResult => T) => Try[T] = Using(
    new ClassGraph().verbose().enableAllInfo().acceptPackages(packageName)
      .scan()
  )

  def allComparable(): Try[Set[Class[_ <: Comparable[_]]]] = using{
     scanResult =>
       val classes = scanResult.getClassesImplementing(classOf[Comparable[_]])
       val clazz = classes.loadClasses(classOf[Comparable[_]])
       clazz.asScala.toSet
  }
  def allComparators(): Try[Set[Class[_ <: Comparator[_]]]] = using{
    scanResult =>
      val classes = scanResult.getClassesImplementing(classOf[Comparator[_]])
      val clazz = classes.loadClasses(classOf[Comparator[_]])
      clazz.asScala.toSet
  }

  def allFieldsDefiningComparators(): Try[Set[Field]] = using{
    scanResult =>
      val fields: List[Field] = for {
        classes <- scanResult.getAllClasses.asScala.toList
        field <- classes.getFieldInfo.asScala.toList
      } yield
        println(field)
        println(field.getTypeDescriptor)
        field.loadClassAndGetField()
      fields.toSet
  }

object Scanner:
  def apply(packageName : String) : Scanner =
    require(packageName!=null,"null no legal package name")
    new Scanner(packageName)
end Scanner

