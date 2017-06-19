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
    canvas.width = 1200
    canvas.height = 1000
    val stage = new Stage(canvas)
    val stageWidth = stage.canvas.width
    val stageHeight = stage.canvas.height
    val message = new Text("Now Loading... ", "18px Arial", "#ffffff")
    stage.addChild(message)
    message.x = 50
    message.y = 100

    val manifest = js.Array(
        js.Dictionary("src" -> "data/image/background.jpg", "type" -> "image", "id" -> "background"),
        js.Dictionary("src" -> "data/image/character.png", "type" -> "image", "id" -> "character"),
        js.Dictionary("src" -> "data/image/mapchips01.png", "type" -> "image", "id" -> "tile1")
    )
    //↑ファイル読み込みにどれくらいかかるかテストのため関連ファイルを全て読み込み

    val loader = new LoadQueue(false)
    loader.loadManifest(manifest)
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
      //一番下にくるマップ用スプライトシート作成
      val tileA2SpriteSheet = new SpriteSheet(js.Dictionary(
        "images" -> js.Array(loader.getResult("tile1")),
        "frames" -> js.Dictionary("width" -> 32, "height" -> 32)
      ));
      val tileA2Sprite= new Sprite(tileA2SpriteSheet);
      val backgroundMap =new Container;
      val mapData = Array.ofDim[String](10, 10);
      for (x <- 0 to 9; y <- 0 to 9) {
        var mapSprite = tileA2Sprite.clone();
        mapSprite.setTransform(x * 32, y * 32);
        mapSprite.gotoAndStop(mapData(y)(x));
        backgroundMap.addChild(mapSprite);
      }
      val knight = new Sprite(spriteSheet)

      stage.addChild(background, backgroundMap, knight)
      stage.update()
      true
    })
  }
}
