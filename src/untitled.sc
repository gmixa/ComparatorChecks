
import java.lang.Comparable
import java.lang.reflect.{Field, ParameterizedType, Type}
import java.util
import java.util.Comparator
import scala.annotation.static
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}
import io.github.classgraph.ClassGraph
import io.github.classgraph.ScanResult
val packageName = ""
def using[T]: (ScanResult => T) => Try[T] = Using(
  new ClassGraph().verbose().enableAllInfo().acceptPackages(packageName)
    .scan()
)
def allFieldsDefiningComparators(): Try[Set[Field]] = using {
  scanResult =>
    val fields: List[Field] = for {
      classes <- scanResult.getAllClasses.asScala.toList
      fields <- classes.getFieldInfo.asScala.toList
    } yield
      println(fields)
      fields.loadClassAndGetField()
    fields.toSet
}