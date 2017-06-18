package com.houkai.app

import com.scalawarrior.scalajs.createjs._
import org.scalajs.dom
import dom.document
import scala.scalajs.js
import scala.scalajs.js.JSApp

object MainGame extends JSApp {
  def main(): Unit = {
    dom.window.addEventListener("load", (_: dom.Event) => onLoad())
  }

  def onLoad(): Unit = {
    val canvas = document.getElementById("game-canvas").asInstanceOf[dom.html.Canvas]
    val stage = new Stage(canvas)
    val stageWidth = stage.canvas.width
    val stageHeight = stage.canvas.height
    val message = new Text("Now Loading... ", "24px Arial", "#ffffff")
    stage.addChild(message)
    message.x = 50
    message.y = 100

    val manifest = js.Array(
        js.Dictionary("src" -> "data/image/background.jpg", "type" -> "image", "id" -> "background"),
        js.Dictionary("src" -> "data/image/character.png", "type" -> "image", "id" -> "character"),
        js.Dictionary("src" -> "http://jsrun.it/assets/k/X/7/w/kX7w7.png", "type" -> "image", "id" -> "TileA1"),
        js.Dictionary("src"-> "http://jsrun.it/assets/i/P/2/3/iP23u.png", "type" -> "image", "id" -> "TileA2"), //横16×縦12　1～192
        js.Dictionary("src" -> "http://jsrun.it/assets/3/E/w/L/3EwLA.png", "type" -> "image", "id" -> "TileA3"), //横16×縦8　1～128
        js.Dictionary("src" -> "http://jsrun.it/assets/l/d/Q/L/ldQLW.png", "type" -> "image", "id" -> "TileA4") //横16×縦15　1～240
    )
    //↑ファイル読み込みにどれくらいかかるかテストのため関連ファイルを全て読み込み

    val loader = new LoadQueue()
    loader.addEventListener("progress", (e: Object) => {
      val event = e.asInstanceOf[ProgressEvent]
      message.text = "Now Loading... " + Math.floor(event.progress * 100)+ "%"
      stage.update()
      true
    })
    loader.addEventListener("complete", (e: Object) => {
      // Assemble shapes on the stage
      val background = new Shape()
      background.graphics.beginBitmapFill(loader.getResult("background")).drawRect(0, 0, stageWidth, stageHeight)

      var spriteSheet = new SpriteSheet(js.Dictionary(
        "framerate" -> 30,
        "images"    -> js.Array(loader.getResult("character")),
        "frames"    -> js.Dictionary("regX" -> 0, "height" -> 32, "count" -> 12, "regY" -> 0, "width" -> 32)
      ))
      val knight = new Sprite(spriteSheet)

      stage.addChild(background, knight)
      stage.update()
      true
    })
    loader.loadManifest(manifest)
  }
}
