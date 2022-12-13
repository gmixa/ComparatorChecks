package de.mixas.generators

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.util.matching.Regex

class URIGenSpec extends AnyFlatSpec with Matchers with ScalaCheckPropertyChecks:

  val uriPattern: Regex =
    """(https?|ftp|file)://(www.)?\p{Graph}+\.\p{Graph}{1,6}/?""".r
  val isValidURI: String => Assertion = uriPattern.findAllMatchIn(_) should have size 1

  behavior of "UriGen"

  it should "generate valid uris" in forAll(URIGen.uri)(isValidURI.compose(_.uri))