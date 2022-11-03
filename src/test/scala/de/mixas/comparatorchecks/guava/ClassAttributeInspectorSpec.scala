package de.mixas.comparatorchecks.guava

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyTestClass, MyTestSubClass}

class ClassAttributeInspectorSpec extends AnyFlatSpec with should.Matchers {

  ignore should "return a List of size 3 after evaluating MyTestClass" in {
    val attributes = ClassAttributeInspector.attributes(classOf[MyTestClass])
    attributes.size should be(3)
  }

  ignore should "return a List of size 1 after evaluating MyTestSubClass" in {
    val attributes = ClassAttributeInspector.attributes(classOf[MyTestSubClass])
    attributes.size should be(1)
  }

  ignore should "return a List of size 3 after evaluating MyTestClass with superclass" in {
    val attributes = ClassAttributeInspector.attributesWithSuperclass(classOf[MyTestClass])
    attributes.size should be(3)
  }

  ignore should "return a List of size 4 after evaluating MyTestSubClass with superclass" in {
    val attributes = ClassAttributeInspector.attributesWithSuperclass(classOf[MyTestSubClass])
    attributes.size should be(4)
  }

}
