package com.github.hfgii.sandbox

import scala.language.experimental.macros

import scala.annotation.StaticAnnotation

class itsjustcode extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro itsjustcodeMacro.impl
}