package com.houkai.app.view

import com.houkai.app.model.OnFieldModel

object OnFieldView {
  val MAGNIFICATION: (Int, Int) = (32, 32)
}

abstract class OnFieldView(val model: OnFieldModel) {
  def setVisible(): Unit

  def getRelativePlace(): (Int, Int) = {
    val place = model.getRelativePlace()
    (OnFieldView.MAGNIFICATION._1 * place._1, OnFieldView.MAGNIFICATION._2 * place._2)
  }

  def getAbsolutePlace(): (Int, Int) = {
    (OnFieldView.MAGNIFICATION._1 * model.currentPlace._1, OnFieldView.MAGNIFICATION._2 * model.currentPlace._2)
  }
}
