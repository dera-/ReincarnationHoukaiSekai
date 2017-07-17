package com.houkai.app.model

abstract class OnFieldModel(val currentPlace: (Int, Int)) {
  def getRelativePlace(): (Int, Int)
}
