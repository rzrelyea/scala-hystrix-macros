package com.github.hfgiii.hystrix

import com.netflix.hystrix.HystrixCommand

class ExampleApiHystrixImpl(@hystrix wrapped: ExampleApi) extends ExampleApi {
  def method1() = "hello world from method1(), instrumented"
  def method2(p1: String) = 41L
}
