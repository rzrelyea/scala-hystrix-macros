package com.github.hfgiii.hystrix

import com.netflix.hystrix.HystrixCommand

class HystrixInstrumentedApi(@hystrix wrapped: UnInstrumentedApi) extends UnInstrumentedApi {
  def method1() = "Hello???"
  def method2(p1: String) = 41L
}
