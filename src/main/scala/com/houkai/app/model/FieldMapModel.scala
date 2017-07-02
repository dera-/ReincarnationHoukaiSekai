package com.houkai.app.model

import com.houkai.app.model.entity.MapChipEntity

class FieldMapModel(mapChipIds :Array[Array[Int]]){
  var mapchipEntities: Array[Array[MapChipEntity]] = mapChipIds.map{line => line.map{i => MapChipEntity.get(i)}}
  def getMapChipEntity(place: (Int, Int)): MapChipEntity = {
    this.mapchipEntities(place._2)(place._1)
  }
  def isInMap(place: (Int, Int)): Boolean ={
    place._2 < this.mapchipEntities.length && place._1 < this.mapchipEntities(place._2).length
  }
}
