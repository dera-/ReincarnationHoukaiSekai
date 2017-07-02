package com.houkai.app.model

import com.houkai.app.model.entity.RouteEntity

trait FieldMovingObject {
  protected var mapModel: FieldMapModel = null

  def setMapModel(mapModel: FieldMapModel): Unit = {
    this.mapModel = mapModel
  }

  def getRoutes(routes: List[RouteEntity], currentRoute: RouteEntity, restPoint: Int): List[RouteEntity] = {
    restPoint match {
      case r if r <= 0 => routes
      case _ => {
        val currentPlace = currentRoute.getCurrentPlace()
        val nextPlaces: Array[(Int, Int)] = Array(
          (currentPlace._1 + 1, currentPlace._2),
          (currentPlace._1 - 1, currentPlace._2),
          (currentPlace._1, currentPlace._2 + 1),
          (currentPlace._1, currentPlace._2 - 1)
        )
        var newRoutes: List[RouteEntity] = Nil
        for (place <- nextPlaces) {
          val places = currentRoute.places.clone() :+ place
          val newRoute = new RouteEntity(places)
          if (this.mapModel.isInMap(place) && !this.isDeplicated(newRoute, routes)) {
            val chipEntity = this.mapModel.getMapChipEntity(place)
            newRoutes = newRoutes ::: this.getRoutes(routes :+ newRoute, newRoute, restPoint - chipEntity.cost)
          }
        }
        newRoutes
      }
    }
  }

  private def isDeplicated(targetRoute: RouteEntity, pastRoutes: List[RouteEntity]): Boolean = {
    pastRoutes.exists(route => targetRoute.isSame(route))
  }
}
