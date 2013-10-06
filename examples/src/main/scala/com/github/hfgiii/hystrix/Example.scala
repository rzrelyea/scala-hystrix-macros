package com.github.hfgiii.hystrix


object  Example extends App {
  def invokeMethodsOnUnInstrumentedApi(foo: UnInstrumentedApi) {
    println("Method 1 result: " + foo.method1())
    println("Method 2 result: " + foo.method2("x"))
    println("Method 3 result: " + foo.method3("y"))
    println("Method 4 result: " + foo.method4("z", 10L))
  }

  val original :HystrixInstrumentedApi = new UnInstrumentedApiImpl
  // val wrapped = new HystrixInstrumentedApi(original)

  //  println("Original:")
  invokeMethodsOnUnInstrumentedApi(original)

  println("---------")
  //  println("Wrapped:")
  //  invokeMethodsOnFoo(wrapped)

  println("Hello!!!!")
}