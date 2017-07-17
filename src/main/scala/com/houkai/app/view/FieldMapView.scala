package com.houkai.app.view

import scala.scalajs.js
import com.scalawarrior.scalajs.createjs._
import com.houkai.app.core.GameManager
import com.houkai.app.model.entity.FieldMapEntity
import com.houkai.app.model.{FieldMapModel, GameCharacterOnFieldModel}

object FieldMapView {
  val BACK_GROUND: String = "background"
  val MAP_CHIP_SIZE: (Int, Int) = (32, 32)

  def generate(id: Int): FieldMapView = {
    FieldMapModel.register(id)
    val model = FieldMapModel.get()
    val container = this.generateMapContainer(model.entity.mapKey, model.entity.getMapChipIds())
    new FieldMapView(model, container)
  }

  private def generateMapContainer(key: String, mapChipIds: Array[Array[Int]]): Container = {
    val tileA2SpriteSheet = new SpriteSheet(js.Dictionary(
      "images" -> js.Array(GameManager.getImage(key)),
      "frames" -> js.Dictionary("width" -> 32, "height" -> 32)
    ))
    val tileA2Sprite= new Sprite(tileA2SpriteSheet)
    val backgroundMap =new Container
    val width = mapChipIds(0).length
    val height = mapChipIds.length
    for (x <- 0 to width-1; y <- 0 to height-1) {
      var mapSprite = tileA2Sprite.clone()
      mapSprite.setTransform(x * 32, y * 32)
      mapSprite.gotoAndStop(mapChipIds(y)(x).toString)
      backgroundMap.addChild(mapSprite)
    }
    backgroundMap
  }
}

class FieldMapView(model: FieldMapModel, val mapContainer: Container) extends OnFieldView(model){
  def getContainer(): Container = {
    this.mapContainer
  }

  override def setVisible(): Unit = {
    val place = this.getRelativePlace()
    this.mapContainer.x = place._1
    this.mapContainer.y = place._2
    this.mapContainer.visible = true
  }
}
