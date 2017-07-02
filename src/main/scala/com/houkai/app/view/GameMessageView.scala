package com.houkai.app.view

import com.scalawarrior.scalajs.createjs._

class GameMessageView(text: String, font: String, color: String, x: Int, y: Int) {
  private val message = new Text(text, font, color)
  message.x = x
  message.y = y
  def getText(): Text = {
    this.message
  }
  def setText(text: String): Unit = {
    this.message.text = text
  }
}
