import cats.*
import cats.implicits.*
import cats.kernel.laws.discipline.OrderTests
import org.scalacheck.*
import org.scalacheck.rng.Seed
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import tests.MyTestClass


class MyTestClassSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:

  given randomMyTestClass : Arbitrary[MyTestClass] = Arbitrary( for {
    x <- Arbitrary.arbitrary[Int]
    y <- Arbitrary.arbitrary[Int]
  } yield new MyTestClass(x,y))

  given cogenClazz : Cogen[MyTestClass] = Cogen { (seed : Seed , clazz : MyTestClass) =>
    val seed1 = Cogen.perturb(seed,clazz.getX)
    Cogen.perturb(seed1,clazz.getY)
  }

  import scala.math.Ordering._

  val myOrdering : Ordering[MyTestClass]= comparatorToOrdering(MyTestClass.Comparators.DEFAULT.reversed())

  given catsMyTestClassorder : Order[MyTestClass] = Order.fromOrdering(myOrdering)
  checkAll("tests.MyTestClass.OrderLaws",OrderTests[MyTestClass].order)

