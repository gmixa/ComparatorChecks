package de.mixas.comparatorchecks.classgraph


import java.lang.reflect.{Field, Modifier}
import scala.annotation.tailrec
import scala.jdk.CollectionConverters.*
import scala.util.Try

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
private class ClassAttributeInspector(packageName : String) extends Scanning(packageName):
  /**
   * determines all fields of the given java class that we have to take regard of.
   *
   * @param clazz java class definition
   * @return a sequence of all relevant class fields
   */
  def attributes(clazz: Class[_]): Try[Seq[Field]] = using {
    scanResult =>
      val classes = scanResult.getAllClasses.asScala.toList
      val classE = classes.filter(c => c.getName == clazz.getName)
      val fields = classE.head.getFieldInfo.asScala.toList
      fields.map(f => f.loadClassAndGetField())
  }
  end attributes
end ClassAttributeInspector

object ClassAttributeInspector:
  def apply(packageName : String) : ClassAttributeInspector =
    new ClassAttributeInspector(packageName)
  end apply
end ClassAttributeInspector
