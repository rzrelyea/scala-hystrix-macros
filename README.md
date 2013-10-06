scala-hystrix-macros
====================

Scala macros to generate Hystrix instrumentation for Scala methods/functions or Java methods. 

* The first macro written generates the following wrapper code around scala class/trait methods and java methods:

  ```
  def instrumentedMethodN(args:In *):Out =
     new HystrixCommand[Out](HystrixConfigurator("<wrapped class Name>","<wrapped/unwrapped method name>",THREAD) {
        override protected def getFallback:Out =  { println("INSIDE FALLBACK!!!!!!!!!!!!!") ; instanceOf[Out].zero }
        override protected def run:Out         =  { "println("OMG!!"); uninstrumentedInstance.uninstrumentedMethodN(args)}
     }.queue.get
  ```
  
* The starting point for this implementation is the [@delegate annotation macro](https://github.com/adamw/scala-macro-aop) by Adam Warski. An example of its use is found 
  
  ```
  package object hystrix {
    implicit def toHystrix(instance:Unwrapped):Wrapped = new Wrapped(instance) // Wrapped <:< Unwrapped
    …
  }
  ``` 
  Thus:
  
  ```
   import hystrix._
   
   // Produces a instance of Wrapped where all Unwrapped methods instrumented with Hystrix 
   // Typing "wrapped" as "Wrapped" force the compiler to relie on "toHystrix" generate
   // the proper type-correct instantiation
   //
   val wrapped:Wrapped = new Unrwapped(args,…)    
  ```
  This macro is inflexible and not sufficient for all Hystrix scenarios:
  * It wraps all Unwrapped methods
  * It does not provide for fallback functions, either by default or by method.
  * It does not provide for HystrixCommand settings: command group, command instance, etc
  
  
  Perhaps a more sophisticated version of the annotation will suffice.
