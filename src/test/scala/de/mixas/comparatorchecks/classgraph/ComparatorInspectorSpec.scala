package de.mixas.comparatorchecks.classgraph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyIntComparator, MyTestClass, MyTestClassComparator}

import java.lang.reflect.Type
import java.util.Comparator
import scala.util.Try

class ComparatorInspectorSpec extends AnyFlatSpec with should.Matchers:

  "ComparatorInspector" should "return 'tests.MyTestClass' type for 'MyTestClassComparator'" in {
    val comparatorInspector = ComparatorInspector("tests")
    val tpe: Try[Option[Type]] = comparatorInspector.comparatorType(classOf[MyTestClassComparator])
    tpe.get.get should equal(classOf[tests.MyTestClass])
  }