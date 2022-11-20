package de.mixas.comparatorchecks.classgraph

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import tests.{MyTestClass, MyTestSubClass}

class ClassAttributeInspectorSpec extends AnyFlatSpec with should.Matchers:

  "ClassAttributeInspector.attributes" should "return a List of size 3 after evaluating MyTestClass" in {
    val attributeScanner = ClassAttributeInspector("tests")
    val attributes = attributeScanner.attributes(classOf[MyTestClass])
    attributes.get.size should be(5)
  }

  it should "return a List of size 1 after evaluating MyTestSubClass" in {
    val attributesScanner = ClassAttributeInspector("tests")
    val attributes = attributesScanner.attributes(classOf[MyTestSubClass])
    attributes.get.size should be(6)
  }

