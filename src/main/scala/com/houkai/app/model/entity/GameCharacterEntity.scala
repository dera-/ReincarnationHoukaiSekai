package com.houkai.app.model.entity

object GameCharacterEntity {
  private val dictionary: Map[Int, GameCharacterEntity] = Map(1 -> new GameCharacterEntity("テスト","character",100,100,20,20,20,5))
  def get(id: Int): GameCharacterEntity = {
    this.dictionary.get(id) match {
      case Some(v) => v
      case None => throw new Exception(s"character${id}は存在しないキーです")
    }
  }
}

class GameCharacterEntity(val name : String, val key : String, val hp: Int, val mp: Int, val attack: Int, val defense: Int, val speed: Int, val move: Int);