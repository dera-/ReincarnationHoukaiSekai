package com.houkai.app.model

import scala.collection.immutable.Map
import com.houkai.app.model.entity.{FieldMapEntity, MapChipEntity, Road}

object FieldMapModel {
  private var model: FieldMapModel = null
  def register(id: Int): Unit = {
    //TODO 今は固定のmapを登録しているが、最終的にはDBから取得したやつを登録できるようにする
    val entity = new FieldMapEntity("tile1", "1,1,1,1,1,1,1¥n1,32,1,1,1,1,1¥n32,1,1,1,1,1,32¥n1,1,1,1,1,1,32¥n1,1,1,1,1,1,1")
    this.model = new FieldMapModel(entity)
  }

  def get(): FieldMapModel = {
    Option(this.model) match {
      case Some(f: FieldMapModel) => f
      case None => throw new Exception("FieldMapModelは空なので取得できない")
    }
  }
}

class FieldMapModel(val entity: FieldMapEntity, currentViewPoint: (Int, Int) = (0, 0)) extends OnFieldModel(currentViewPoint) {
  override def getRelativePlace(): (Int, Int) = {
    (0, 0)
  }

  def isMovable(place: (Int, Int)): Boolean = {
    this.isInMap(place) && this.isRoad(place)
  }

  def isInMap(place: (Int, Int)): Boolean = {
    0 <= place._2 && place._2 < this.entity.getHeight() && 0 <= place._1 && place._1 < this.entity.getWidth()
  }

  def isRoad(place: (Int, Int)): Boolean = {
    this.entity.getMapChipEntity(place).fieldType match {
      case Road => true
      case _ => false
    }
  }
}
