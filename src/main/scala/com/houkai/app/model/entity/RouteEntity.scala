package com.houkai.app.model.entity

class RouteEntity(val places: List[(Int, Int)]) {
  def isSame(route: RouteEntity): Boolean = {
    val distance = route.places.length
    if (distance != this.places.length) {
      return false
    }
    (0 to distance - 1).forall(i => places(i)._1 == route.places(i)._1 && places(i)._2 == route.places(i)._2)
  }

  def getReachPlace(): (Int, Int)= {
    places.last
  }
}
