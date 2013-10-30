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
    println("size of list: " + inputs.size)
    println("head tree: " + show(inputs.head))
    println("head tree raw: " + showRaw(inputs.head))
    val method: c.universe.Tree = inputs.head
    
    val wrappedMethod: c.universe.DefDef = method match {
      //DefDef(Modifiers(), newTermName("hw"), List(), List(List()), 
      //  Ident(newTypeName("Unit")), Apply(Ident(newTermName("println")), 
      //  List(Literal(Constant("hw")))))
      case  DefDef(mods, name, tparams, vparamss, tpt, body) => {
        println("matched defdef")
        println("raw body: " + showRaw(body))
        val newBlock = q""" println("hello world");  """
        val newBody = newBlock :: body.children
        
        val newMethod = DefDef(mods, name, tparams, vparamss, tpt, 
        		Block(newBody, Literal(Constant(()))))
        println("new method: " + show(newMethod))
        newMethod
      }
      case _ => {
        reportInvalidAnnotationTarget()
        val ret =
          DefDef(Modifiers(),
            newTermName("hw"),
            List(), // TODO - type parameters
            List(List()),
            Ident(newTypeName("Unit")),
            Apply(Ident(newTermName("println")), List(Literal(Constant("hw")))))
        ret
      }
    }
    val output = List(wrappedMethod)
    c.Expr[Any](Block(output, Literal(Constant(()))))
    //reify { println("Hello World!") }
  }
}