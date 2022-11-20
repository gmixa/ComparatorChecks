package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.URIGen
import org.scalacheck.{Arbitrary, Cogen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.net.URI

class URISpec extends AnyFunSuite with FunSuiteDiscipline with Configuration :
  given Arbitrary[java.net.URI] = Arbitrary(URIGen.uri.map(u => new URI(u.uri))
  )

  given Cogen[java.net.URI] = Cogen(u => u.toString.length)

  import scala.math.Ordering.*

  val uriDefaultOrder: Ordering[java.net.URI] = ordered[java.net.URI]

  given Order[java.net.URI] = Order.fromOrdering(uriDefaultOrder)

  checkAll("java.net.URI", OrderTests[java.net.URI].order)

end URISpec
