package de.mixas.comparatorchecks.reflections

import de.mixas.comparatorchecks.refelections.ComparatorInspector
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyIntComparator, MyTestClass}

import java.util.Comparator

class ComparatorInspectorSpec extends AnyFlatSpec with should.Matchers{

  "ComparatorInspector" should "return 'java.lang.Integer' type for an Comparator[Int]" in {
    val tpe = ComparatorInspector.comparatorType(classOf[MyIntComparator])
    tpe.get should equal(classOf[java.lang.Integer])
  }

}
