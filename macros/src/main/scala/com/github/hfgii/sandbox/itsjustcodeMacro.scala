package com.github.hfgii.sandbox

import scala.reflect.macros.Context
import scala.language.experimental.macros

object itsjustcodeMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    
    def reportInvalidAnnotationTarget() {
      c.error(c.enclosingPosition, "This annotation can only be used on methods/functions")
    }
    
    val inputs:List[c.universe.Tree] = annottees.map(_.tree).toList
    println("tree: " + show(inputs.head))
    println("tree raw: " + showRaw(inputs.head))
    val method: c.universe.Tree = inputs.head
    
    val wrappedMethod = method match {
      //DefDef(Modifiers(), newTermName("hw"), List(), List(List()), 
      //  Ident(newTypeName("Unit")), Apply(Ident(newTermName("println")), 
      //  List(Literal(Constant("hw")))))
      case DefDef(mods, name, tparams, vparamss, tpt, body) => {
        println("matched defdef")
        //new DefDef(mods, tparams, vparamss, tpt, reify { println("Hello World!") })
      }
      case _ => {
        reportInvalidAnnotationTarget()
        //new DefDef(null, null, null, null, null)
      }
    }

    reify { println("Hello World!") }
  }
}