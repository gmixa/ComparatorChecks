package jdkinternals

import cats.Order
import cats.kernel.laws.discipline.OrderTests
import de.mixas.generators.ShortBufferGen
import org.scalacheck.rng.Seed
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import java.math.BigInteger
import java.net.URI
import java.nio.ByteBuffer
import java.time.chrono.IsoChronology
import java.time.temporal.{ChronoField, ChronoUnit, TemporalUnit}
import java.time.{Duration, Instant, LocalDate, LocalDateTime, LocalTime, MonthDay, OffsetDateTime, ZoneOffset}
import scala.collection.mutable
import scala.math.Ordering.ordered

class ByteSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
  given Arbitrary[java.lang.Byte] = Arbitrary(
    Gen.chooseNum(Byte.MinValue, Byte.MaxValue).map(java.lang.Byte.valueOf)
  )
  given Cogen[java.lang.Byte] = Cogen(_.toByte)
  import scala.math.Ordering.*
  val byteDefaultOrder: Ordering[java.lang.Byte] = ordered[java.lang.Byte]
  given Order[java.lang.Byte] = Order.fromOrdering(byteDefaultOrder)
  checkAll("java.lang.Byte", OrderTests[java.lang.Byte].order)
end ByteSpec
class ChronologySpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
end ChronologySpec
class ChronoZonedDateTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
end ChronoZonedDateTimeSpec

class OffsetTimeSpec extends AnyFunSuite with FunSuiteDiscipline with Configuration:
end OffsetTimeSpec

class YearSpec

class YearMonthSpec