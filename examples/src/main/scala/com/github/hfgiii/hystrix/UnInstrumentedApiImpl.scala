package com.github.hfgiii.hystrix


class UnInstrumentedApiImpl extends UnInstrumentedApi {
  def method1() = "m1"
  def method2(p1: String) = 42L
  def method3(p1: String) = p1.length()
  def method4(p1: String, p2: Long) =  { p2/0 ; s"ok $p1 $p2" }
}