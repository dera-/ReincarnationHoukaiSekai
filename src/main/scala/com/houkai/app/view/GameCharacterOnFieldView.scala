package com.houkai.app.view

import com.scalawarrior.scalajs.createjs._
import scala.scalajs.js
import com.houkai.app.core._
import com.houkai.app.model.GameCharacterOnFieldModel

object GameCharacterOnFieldView {
  def generate(charaId: Int, place:(Int, Int)): GameCharacterOnFieldView = {
    val model = GameCharacterOnFieldModel.generate(charaId, place)
    val sprite = this.generateSprite(model.entity.key)
    new GameCharacterOnFieldView(model, sprite)
  }

  private def generateSprite(key : String): Sprite = {
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
    val characterSprite = new Sprite(spriteSheet)
    val hitObject = new Shape()
    hitObject.graphics.beginFill("#000000").drawRect(0, 0, characterSprite.getBounds().width, characterSprite.getBounds().height)
    characterSprite.hitArea = hitObject
    characterSprite
  }
}

class GameCharacterOnFieldView(model: GameCharacterOnFieldModel, val sprite: Sprite) extends OnFieldView(model){
  this.sprite.addEventListener(
    "click",
    (e :Object) => {
      val event = e.asInstanceOf[MouseEvent]
      FieldEvent.showCurrentEvent() match {
        case Thinking => {
          this.model.calculateRoutes()
          GameManager.addToCurrentStage(this.getMovablePlaces())
          FieldEvent.addEvent(BeforeMoving)
        }
        case _ => true
      }
      true
    },
    false
  )

  def getSprite(): Sprite = {
    this.sprite
  }

  override def setVisible(): Unit = {
    val place = this.getRelativePlace()
    this.sprite.x = place._1
    this.sprite.y = place._2
    this.sprite.visible = true
  }

  def playAction(eventName: String): Unit = {
    this.sprite.gotoAndPlay(eventName)
  }
  def addEventListener(name: String, event: js.Function1[Object, Boolean]): Unit = {
    this.sprite.addEventListener(name, event, false)
  }

  // 移動可能箇所の矩形を返す
  def getMovablePlaces(): Container = {
    val chipWidth = FieldMapView.MAP_CHIP_SIZE._1
    val chipHeight = FieldMapView.MAP_CHIP_SIZE._2
    val relativePlace = this.model.getRelativePlace()
    val realViewPoint = (chipWidth * (this.model.currentPlace._1 - relativePlace._1), chipHeight * (this.model.currentPlace._2 - relativePlace._2))
    val routes = this.model.getRoutes()
    val shapes: List[Shape] = routes.map(route => {
      val place = route.last
      val shape = new Shape()
      shape.graphics.beginFill("cyan").drawRect(chipWidth * place._1 - realViewPoint._1, chipHeight * place._2 - realViewPoint._2, 32, 32)
      shape.alpha = 0.5
      shape.addEventListener(
        "click",
        (e :Object) => {
          this.moveAnimation(route)
          true
        },
        false
      )
      shape
    })
    val movablePlaceContainer: Container = new Container()
    shapes.foreach(shape => movablePlaceContainer.addChild(shape))
    movablePlaceContainer.addEventListener(
      "click",
      (e :Object) => {
        FieldEvent.showCurrentEvent() match {
          case BeforeMoving => {
            movablePlaceContainer.removeAllChildren()
            FieldEvent.addEvent(AfterMoving)
          }
          case _ => true
        }
        true
      },
      false
    )
    movablePlaceContainer
  }

  private def moveAnimation(route: List[(Int, Int)]): Unit = {
    // TODO スクロールも考慮する
    route.foreach(point => {
      Tween.get(this.sprite).to(
        js.Dictionary("x" -> point._1, "y" -> point._2),
        1000
      )
    })
  }
}
