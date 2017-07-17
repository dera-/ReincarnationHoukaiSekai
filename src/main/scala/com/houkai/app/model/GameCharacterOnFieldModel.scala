package com.houkai.app.model

import com.houkai.app.model.entity.{GameCharacterEntity, RouteEntity}

object GameCharacterOnFieldModel {
  def generate(charaId: Int, place: (Int, Int)): GameCharacterOnFieldModel = {
    val model = new GameCharacterOnFieldModel(GameCharacterEntity.get(charaId), place)
    model.setMapModel(FieldMapModel.get())
    model
  }
}

class GameCharacterOnFieldModel(val entity: GameCharacterEntity, place: (Int, Int)) extends OnFieldModel (place) with FieldMovingObject{
  private var routes: List[RouteEntity] = List.empty

  override def getRelativePlace(): (Int, Int) = {
    Option(this.mapModel) match {
      case Some(map) => (this.place._1 - map.currentPlace._1, this.place._2 - map.currentPlace._2)
      case None => this.place
    }
  }

  def initializeRoutes(): Unit = {
    this.routes = List.empty
  }

  def calculateRoutes(): Unit = {
    this.routes = this.getRoutes(List.empty, new RouteEntity(List(this.currentPlace)), this.entity.move)
  }

  def getRoutes(): List[List[(Int, Int)]] = {
    var notDuplicated: List[List[(Int, Int)]] = List.empty
    this.routes.foreach(route => {
      if (!notDuplicated.exists(r => r.last == route.getReachPlace())) {
        notDuplicated = route.places :: notDuplicated
      }
    })
    notDuplicated
  }
}
