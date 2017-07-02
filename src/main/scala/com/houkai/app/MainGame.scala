package com.houkai.app

import com.scalawarrior.scalajs.createjs._
import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.JSApp
import com.houkai.app.core.GameManager

object MainGame extends JSApp {
  def main(): Unit = {
    dom.window.addEventListener("load", (_: dom.Event) => onLoad())
  }

  def onLoad(): Unit = {
    val canvas = dom.document.getElementById("game-canvas").asInstanceOf[dom.html.Canvas]
    canvas.width = 1200
    canvas.height = 1000
    val manifest = js.Array(
        js.Dictionary("src" -> "data/image/background.jpg", "type" -> "image", "id" -> "background"),
        js.Dictionary("src" -> "data/image/character.png", "type" -> "image", "id" -> "character"),
        js.Dictionary("src" -> "data/image/mapchips01.png", "type" -> "image", "id" -> "tile1")
    )
    GameManager.setCurrentStage(canvas)
    GameManager.preLoad(manifest)
    GameManager.initialize()
  }
}
