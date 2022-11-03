package de.mixas.comparatorchecks.refelections

import org.reflections.Reflections
import org.reflections.scanners.Scanners

import java.lang.Comparable
import java.lang.reflect.{Field, ParameterizedType, Type}
import java.util
import java.util.Comparator
import scala.annotation.static
import scala.jdk.CollectionConverters.*

private class Scanner(reflections : Reflections):
  def allComparable(): Set[Class[_ <: Comparable[_]]] =
    reflections.getSubTypesOf(classOf[Comparable[_]]).asScala.toSet
  def allComparators(): Set[Class[_ <: Comparator[_]]] =
    reflections.getSubTypesOf(classOf[Comparator[_]]).asScala.toSet
  def allFieldsDefiningComparators(): Set[Field] =
    val allClasses: Set[Class[_]] = reflections.getSubTypesOf(classOf[Any]).asScala.toSet
    val fieldsDefiningComparator: Set[Field] =
      for { clazz <- allClasses
            field <- clazz.getFields if field.getType.eq(classOf[Comparator[_]])
          } yield field
    fieldsDefiningComparator
end Scanner

object Scanner:
  def apply(reflections : Reflections) : Scanner=
    require(reflections != null, "reflections must not be null!")
    new Scanner(reflections)
  def apply(packageName : String) : Scanner=
    require(packageName!=null, "packageName must not be null!")
    val reflections = new Reflections(packageName,Scanners.SubTypes.filterResultsBy( _ =>true))
    apply(reflections)
end Scanner

