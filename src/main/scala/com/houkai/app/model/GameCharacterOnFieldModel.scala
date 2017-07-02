package com.houkai.app.model

import com.houkai.app.model.entity.{GameCharacterEntity, RouteEntity}

class GameCharacterOnFieldModel (entity: GameCharacterEntity, place: (Int, Int)) extends FieldMovingObject {
  val characterEntity = entity
  var currentPlace: (Int, Int) = place

  def getMovablePlaces(): List[(Int, Int)] = {
    val routes = this.getRoutes(Nil, new RouteEntity(Array(this.currentPlace)), this.characterEntity.move)
    routes.map(route => route.getCurrentPlace())
  }
}
