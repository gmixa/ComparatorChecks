package de.mixas.comparatorchecks

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyTestSubClass,MyTestClass}

class ClassAttributeInspectorSpec extends AnyFlatSpec with should.Matchers {

  "ClassAttributeInspector.attributes" should "return a List of size 3 after evaluating MyTestClass" in {
    val attributes = ClassAttributeInspector.attributes(classOf[MyTestClass])
    attributes.size should be(3)
  }

  it should "return a List of size 1 after evaluating MyTestSubClass" in {
    val attributes = ClassAttributeInspector.attributes(classOf[MyTestSubClass])
    attributes.size should be(1)
  }

  "ClassAttributeInspector.attributesWithSuperclass" should "return a List of size 3 after evaluating MyTestClass" in {
    val attributes = ClassAttributeInspector.attributesWithSuperclass(classOf[MyTestClass])
    attributes.size should be(3)
  }

  it should "return a List of size 1 after evaluating MyTestSubClass" in {
    val attributes = ClassAttributeInspector.attributesWithSuperclass(classOf[MyTestSubClass])
    attributes.size should be(4)
  }

}
