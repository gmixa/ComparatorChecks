package de.mixas.comparatorchecks.guava

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyIntComparator, MyTestClass}

import java.util.Comparator

class ComparatorInspectorSpec extends AnyFlatSpec with should.Matchers{

  ignore should "return 'java.lang.Integer' type for an Comparator[Int]" in {
    val tpe = ComparatorInspector.comparatorType(classOf[MyIntComparator])
    tpe.get should equal(classOf[java.lang.Integer])
  }

}
