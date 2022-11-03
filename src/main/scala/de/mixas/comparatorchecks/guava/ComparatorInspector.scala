package de.mixas.comparatorchecks.guava

import java.lang.reflect.{ParameterizedType, Type}
import java.util.Comparator

object ComparatorInspector:
  def comparatorType[T <: Comparator[_ <: Any]](clazz: Class[T]): Option[Type] = ???
  
end ComparatorInspector
