package de.mixas.comparatorchecks.classgraph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyIntComparator, MyTestClass, MyTestClassComparator}

import java.util.Comparator

class ComparatorInspectorSpec extends AnyFlatSpec with should.Matchers{

  "ComparatorInspector" should "return 'java.lang.Integer' type for an Comparator[Int]" in {
    val comparatorInspector = ComparatorInspector("tests")
    val tpe = comparatorInspector.comparatorType(classOf[MyTestClassComparator])
    tpe.get.get should equal(classOf[tests.MyTestClass])
  }

}
