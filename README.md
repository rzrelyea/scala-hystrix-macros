scala-hystrix-macros
====================

Scala macros to generate Hystrix instrumentation for Scala methods/functions or Java methods. 

* The first macro written will generate the following wrapper (pseudo) code around scala class/trait methods and java methods:

  ```
  def wrappedMethodN(args:In *):Out =
     new HystrixCommand[Out](SettingsInstance) {
        override protected def getFallback:Out = fallback() //() => Out
        override protected def run:Out         = unwrappedInstance.unwrappedMethodN(args)
     }.queue.get
  ```
  
* The actual macro implementation still requires more study of various of scala macros types: def, implicit, annotation. A good starting point is the [@delegate annotation macro](https://github.com/adamw/scala-macro-aop). An example usage might be something like:
  
  ```
  package object hystrix {
    implicit def toHystrix(instance:Unwrapped):Wrapped = new Wrapped(@hystrix instance) // Wrapped <:< Unwrapped
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
