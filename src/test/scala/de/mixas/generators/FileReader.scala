package de.mixas.generators

import scala.io.Source

object FileReader {
  lazy val uris: Vector[String] = readFromFile("uris")

  private def readFromFile(file: String): Vector[String] =
    Source.fromResource(file).getLines().toVector

}
