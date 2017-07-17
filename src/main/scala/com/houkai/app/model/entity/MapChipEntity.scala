package com.houkai.app.model.entity

sealed abstract class FieldType(val index: Int)
case object Road extends FieldType(0)
case object Obstacle extends FieldType(1)

object MapChipEntity {
  private val dictionary: Map[Int, MapChipEntity] = Map(
    1 -> new MapChipEntity(9, "草原", Road, 0f, 0f, 0f, 1),
    32 -> new MapChipEntity(1, "壁", Obstacle, 0f, 0f, 0f, 1)
  )

  def get(id: Int): MapChipEntity = {
    dictionary.get(id) match {
      case Some(v) => v
      case None => throw new Exception("mapchip"+id+"は存在しないキーです")
    }
  }
}

class MapChipEntity(val id: Int, val name: String, val fieldType: FieldType, val refresh: Float, val defense: Float, val speed: Float, val cost: Int)
