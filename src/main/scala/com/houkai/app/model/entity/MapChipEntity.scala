package com.houkai.app.model.entity

object MapChipEntity {
  private val dictionary = Map[Int, MapChipEntity]()
  this.dictionary + (1 -> new MapChipEntity("草原", 0f, 0f, 0f, 1))
  def get(id: Int): MapChipEntity = {
    dictionary.get(id) match {
      case Some(v) => v
      case None => throw new Exception("存在しないキーです")
    }
  }
}

class MapChipEntity(val name: String, val refresh: Float, val defense: Float, val speed: Float, val cost: Int)
