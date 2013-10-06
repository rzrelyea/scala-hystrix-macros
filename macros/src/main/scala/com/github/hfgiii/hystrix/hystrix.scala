package com.github.hfgiii.hystrix

import scala.language.experimental.macros

import scala.annotation.StaticAnnotation

class hystrix extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro hystrixMacro.impl
}
