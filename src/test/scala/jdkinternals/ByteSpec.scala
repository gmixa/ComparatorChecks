package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.{ByteBufferGen, CharBufferGen, DoubleBufferGen, URIGen}
import org.scalacheck.rng.Seed
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.math.BigInteger
import java.net.URI
import scala.collection.mutable

class ByteSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[java.lang.Byte] = Arbitrary(
    Gen.chooseNum(Byte.MinValue, Byte.MaxValue).map(java.lang.Byte.valueOf)
  )
  given Cogen[java.lang.Byte] = Cogen(_.toByte)
  import scala.math.Ordering.*
  val byteDefaultOrder: Ordering[java.lang.Byte] = ordered[java.lang.Byte]
  given Order[java.lang.Byte] = Order.fromOrdering(byteDefaultOrder)
  checkAll("java.lang.Byte", OrderTests[java.lang.Byte].order)

class FloatBufferSpec

class IntBufferSpec

class LongBufferSpec

class ShortBufferSpec

class CollationKeySpec

class CollatorSpec

class ChronoLocalDateSpec

class ChronoLocalDateTimeSpec

class ChronologySpec

class ChronoZonedDateTimeSpec

class DurationSpec

class InstantSpec

class LocalTimeSpec

class MonthDaySpec

class OffsetDateTimeSpec

class OffsetTimeSpec

class YearSpec

class YearMonthSpec