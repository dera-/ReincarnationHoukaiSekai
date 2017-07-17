package com.houkai.app.model.entity

class FieldMapEntity(val mapKey: String, val mapString: String) {
  //TODO 安全に型変換したいね
  private val mapChipEntities: Array[Array[MapChipEntity]] = (this.mapString split "¥n").map{line => (line split ",").map{x => MapChipEntity.get(x.toInt)}}

  def getMapChipEntity(place: (Int, Int)): MapChipEntity = {
    this.mapChipEntities(place._2)(place._1)
  }

  def getWidth(): Int = {
    this.mapChipEntities(0).length
  }

  def getHeight(): Int = {
    this.mapChipEntities.length
  }

  def getMapChipIds(): Array[Array[Int]] = {
    this.mapChipEntities.map{line => line.map{e => e.id}}
  }
}
