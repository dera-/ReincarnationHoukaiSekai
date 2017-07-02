package com.houkai.app.view

import scala.scalajs.js
import com.scalawarrior.scalajs.createjs._
import com.houkai.app.core.GameManager

object FieldMapView {
  val BACK_GROUND: String = "background";
  def generateMapSprite(key: String, width: Int, height: Int): Container = {
    val tileA2SpriteSheet = new SpriteSheet(js.Dictionary(
      "images" -> js.Array(GameManager.getImage(key)),
      "frames" -> js.Dictionary("width" -> 32, "height" -> 32)
    ))
    val tileA2Sprite= new Sprite(tileA2SpriteSheet)
    val backgroundMap =new Container
    val mapData = Array.ofDim[String](width, height)
    for (x <- 0 to width-1; y <- 0 to height-1) {
      var mapSprite = tileA2Sprite.clone()
      mapSprite.setTransform(x * 32, y * 32)
      mapSprite.gotoAndStop(mapData(y)(x))
      backgroundMap.addChild(mapSprite)
    }
    backgroundMap
  }
}

class FieldMapView(key: String, width: Int, height: Int) {
  //一番下にくるマップ用スプライトシート作成
  val mapContainer: Container = FieldMapView.generateMapSprite(key, width, height)
  def getContainer(): Container = {
    this.mapContainer
  }
}
