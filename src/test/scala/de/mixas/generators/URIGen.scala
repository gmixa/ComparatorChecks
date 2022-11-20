package de.mixas.generators

import org.scalacheck.Gen.freqTuple
import org.scalacheck.Gen

import scala.io.Source
object URIGen:

  private lazy val uris = FileReader.uris
  private val protocolFrequency = List((10, "http://"), (10, "https://"), (1, "ftp://"), (1, "file://")).map(freqTuple)

  case class URI(uri : String) extends AnyVal

  val uri : Gen[URI] =
    for {
      uri <- Gen.oneOf(uris)
      protocol <- Gen.frequency(protocolFrequency: _*)
      www <- Gen.oneOf("www.","")
    } yield URI(protocol+www+uri)


