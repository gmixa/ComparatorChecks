package de.mixas.comparatorchecks.classgraph

import io.github.classgraph.{ClassGraph, ScanResult}

import scala.util.{Try, Using}

private trait Scanning(packageName : String):
  protected final def using[T]: (ScanResult => T) => Try[T] = Using(
    new ClassGraph().enableAllInfo().acceptPackages(packageName)
      .scan()
  )
end Scanning

