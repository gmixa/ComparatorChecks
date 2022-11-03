package de.mixas.comparatorchecks.classgraph

import java.lang.reflect.{Field, Modifier}
import scala.annotation.tailrec

/**
 * Determines all fields of a class that are non static and non transient.
 * These fields are expected to be relevant for comparing the class.
 * If we want to generate random instances of a class we have to generate a set of classes
 * wich differ in these fields for testing.
 *
 * @see
 * [[https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/builder/CompareToBuilder.html#reflectionCompare-java.lang.Object-java.lang.Object-]]
 * @author Gerald Mixa
 *
 */
object ClassAttributeInspector:
  /**
   * Criteria for fields that we do __not__ want in our list for relevant fields of a class.
   */
  val nonstandardAttribute: Field => Boolean = { field =>
    val modifiers = field.getModifiers
    Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)
  }

  /**
   * determines all fields of the given java class that we have to take regard of, without evaluating the superclass chain
   *
   * @param clazz java class definition
   * @return a sequence of all relevant class fields
   */
  def attributes(clazz: Class[_]): Seq[Field] = ???
    
  def attributesWithSuperclass(clazz: Class[_]): Seq[Field] = ???

end ClassAttributeInspector
