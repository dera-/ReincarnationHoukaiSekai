package com.houkai.app.view

import com.scalawarrior.scalajs.createjs._
import scala.scalajs.js
import com.houkai.app.core.GameManager

object GameCharacterOnFieldView {
  def generateSprite(key : String): Sprite = {
    val spriteSheet = new SpriteSheet(js.Dictionary(
      "framerate"  -> 30,
      "images"     -> js.Array(GameManager.getImage(key)),
      "frames"     -> js.Dictionary("regX" -> 0, "height" -> 32, "count" -> 12, "regY" -> 0, "width" -> 32),
      "animations" -> js.Dictionary(
        "walk" ->  js.Dictionary(
          "frames" -> js.Array(0,2)
        )
      )
    ))
    val clickEvent: js.Function1[Object, Boolean] = (e: Object) => {
      println("called")
      true
    }
    val characterSprite = new Sprite(spriteSheet)
    val hitObject = new Shape()
    hitObject.graphics.beginFill("#000000").drawRect(0, 0, characterSprite.getBounds().width, characterSprite.getBounds().height)
    characterSprite.hitArea = hitObject
    characterSprite.addEventListener("click", clickEvent, false)
    characterSprite
  }
}


class GameCharacterOnFieldView(key :String) {
  val characterSprite : Sprite = GameCharacterOnFieldView.generateSprite(key)

  def getSprite(): Sprite = {
    this.characterSprite
  }

  def playAction(eventName: String): Unit = {
    this.characterSprite.gotoAndPlay(eventName)
  }
}
