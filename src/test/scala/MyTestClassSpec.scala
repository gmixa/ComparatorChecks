import cats._
import cats.implicits._
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck._
import org.scalacheck.rng.Seed
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import scala.math.Ordering

class MyTestClassSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration{

  implicit val randomMyTestClass : Arbitrary[MyTestClass] = Arbitrary( for {
    x <- Arbitrary.arbitrary[Int]
    y <- Arbitrary.arbitrary[Int]
  } yield new MyTestClass(x,y))

  implicit val cogenClazz : Cogen[MyTestClass] = Cogen { (seed : Seed , clazz : MyTestClass) =>
    val seed1 = Cogen.perturb(seed,clazz.getX)
    Cogen.perturb(seed1,clazz.getY)
  }

  import scala.math.Ordering._

  val myOrdering : Ordering[MyTestClass]= comparatorToOrdering(MyTestClass.Comparators.BUGGY.reversed())

  implicit val catsMyTestClassorder : Order[MyTestClass] = Order.fromOrdering(myOrdering)
  checkAll("MyTestClass.OrderLaws",OrderTests[MyTestClass].order)
}
