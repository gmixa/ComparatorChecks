package de.mixas.comparatorchecks

import java.lang.reflect.{Field, Modifier, ParameterizedType, Type}
import java.util.Comparator

object ComparatorInspector:
  def comparatorType[T <: Comparator[_ <: Any]](clazz: Class[T]): Option[Type] =
    val genericInterfaces = clazz.getGenericInterfaces.toList
    val res: List[Type] = for {genericInterface <- genericInterfaces if genericInterface.isInstanceOf[ParameterizedType]}
      yield {
        val parameterizedType = genericInterface.asInstanceOf[ParameterizedType]
        parameterizedType.getActualTypeArguments.head
      }
    require(res.size <= 1, "At most one TypeParameter for Compareable should be found")
    res.headOption
end ComparatorInspector
