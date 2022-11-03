package de.mixas.comparatorchecks.guava

import org.reflections.Reflections
import org.reflections.scanners.Scanners

import java.lang.Comparable
import java.lang.reflect.{Field, ParameterizedType, Type}
import java.util
import java.util.Comparator
import scala.annotation.static
import scala.jdk.CollectionConverters.*

private class Scanner(reflections : Reflections):
  def allComparable(): Set[Class[_ <: Comparable[_]]] = ???
  def allComparators(): Set[Class[_ <: Comparator[_]]] = ???
  def allFieldsDefiningComparators(): Set[Field] = ???

object Scanner:
  def apply(reflections : Reflections) : Scanner= ???
  def apply(packageName : String) : Scanner = ???

end Scanner

