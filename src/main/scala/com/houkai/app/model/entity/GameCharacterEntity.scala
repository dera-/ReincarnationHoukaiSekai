package com.houkai.app.model.entity

object GameCharacterEntity {
  private val dictionary = Map[Int, GameCharacterEntity]()
  this.dictionary + (1 -> new GameCharacterEntity("aa",100,100,20,20,20,5))
  def get(id: Int): GameCharacterEntity = {
    dictionary.get(id) match {
      case Some(v) => v
      case None => throw new Exception("存在しないキーです")
    }
  }
}

class GameCharacterEntity(val name : String, val hp: Int, val mp: Int, val attack: Int, val defense: Int, val speed: Int, val move: Int);