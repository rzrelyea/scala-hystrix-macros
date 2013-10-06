scala-hystrix-macros
====================

Scala macros to generate Hystrix instrumentation for Scala/Java methods. 

* The first macro written generates the following wrapper code around scala/java methods:

  ```
  def instrumentedMethodN(args:In *):Out =
     new HystrixCommand[Out](HystrixConfigurator("<wrapped class Name>","<wrapped/unwrapped method name>",THREAD) {
        override protected def getFallback:Out =  { println("INSIDE FALLBACK!!!!!!!!!!!!!") ; instanceOf[Out].zero }
        override protected def run:Out         =  { "println("OMG!!"); uninstrumentedInstance.uninstrumentedMethodN(args)}
     }.queue.get
  ```
  
* The starting point for this implementation is the [@delegate annotation macro](https://github.com/adamw/scala-macro-aop) by Adam Warski.The *hystrix* macro retains the bulk of Adam's *delegate* macro implementation. The difference lies in the *addDelegateMethods* method, chiefly in the use of *quasiquotes* in writing the trees for the Hystrix method instrumentation. An example of its use is found in the [Example object](https://github.com/hfgiii/scala-hystrix-macros/blob/master/examples/src/main/scala/com/github/hfgiii/hystrix/Example.scala). The following *implicit def* in the [*hystrix* package object](https://github.com/hfgiii/scala-hystrix-macros/blob/master/examples/src/main/scala/com/github/hfgiii/hystrix/package.scala) ,
  
  ```
    implicit def toHystrix(instance:UnInstrumentedApi):HystrixInstrumentedApi = 
          new HystrixInstrumentedApi(instance)
 
  }
  ``` 
  leads to simple invocation of the *hystix* annotation macro 
  
  ```
   import hystrix._
   
   // Produces a instance of HystrixInstrumentedApi with UnInstrumentedApiImpl methods instrumented with Hystrix 
   // Typing "instrumented" as "HystrixInstrumentedApi" forces the compiler to rely on "toHystrix" to generate
   // the proper type-correct instantiation
   //
   val instrumented:HystrixInstrumentedApi = new UnInstrumentedApiImpl   
  ```
  where, the *@hystrix* annotation appears in the declaration of *HystrixInstrumentedApi*:

  ```
class HystrixInstrumentedApi(@hystrix wrapped: UnInstrumentedApi) extends UnInstrumentedApi {
  ...
}
  ``` 

 This macro implementation has several weaknesses and, in its current state acts mainly as a **POC** (Proof Of Concept). The weaknesses are:

  * It does not provide site-specific fallback functions, either at the class or method level.
  * The HystrixCommand settings are re-computed each time a instrumented method is invoked. 
  
  
