package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.{ClassGraph, ScanResult}

import scala.util.{Try, Using}

/**
 * common functionality for the scanner and all inspectors
 */
private trait Scanning(packageName: String):
  /**
   * handles the automatic resource management - autoclosing - of
   * the files that are evaluated
   *
   * @tparam T result type of a scan
   * @return a ScanResult that is used to get the desired information
   */
  protected final def using[T]: (ScanResult => T) => Try[T] = Using(
    new ClassGraph().enableAllInfo().acceptPackages(packageName)
      .scan()
  )
end Scanning

