package com.houkai.app.core

import scala.scalajs.js
import com.scalawarrior.scalajs.createjs._
import org.scalajs.dom
import com.houkai.app.view._

object GameManager {
  private val loader: LoadQueue = new LoadQueue(false)
  private var currentStage: Stage = null
  private var currentDisplayObjects: List[DisplayObject] = List.empty

  def getImage(imageKey: String): Object = {
    this.loader.getResult(imageKey)
  }

  def setCurrentStage(canvas: dom.html.Canvas): Unit = {
    this.currentStage = new Stage(canvas)
  }

  def addToCurrentStage(displayObject: DisplayObject): Unit = {
    this.currentStage.addChild(displayObject)
  }

  def deleteFromCurrentStage(displayObject: DisplayObject): Unit = {
    this.currentStage.removeChild(displayObject)
  }

  def preLoad(manifest: Object): Unit = {
    this.loader.loadManifest(manifest)
  }

  def initialize(): Unit = {
    val messageView = new GameMessageView("Now Loading... ", "18px Arial", "#ffffff", 50, 100)
    this.currentStage.addChild(messageView.getText())
    this.loader.addEventListener("progress", (e: Object) => {
      val event = e.asInstanceOf[ProgressEvent]
      messageView.setText("Now Loading... " + Math.floor(event.progress * 100)+ "%")
      this.currentStage.update()
      true
    })
    this.loader.addEventListener("complete", (e: Object) => {
      // Assemble shapes on the stage
      val background = new Shape()
      val stageWidth = this.currentStage.canvas.width
      val stageHeight = this.currentStage.canvas.height
      background.graphics.beginBitmapFill(this.loader.getResult("background")).drawRect(0, 0, stageWidth, stageHeight)
      val mapView = FieldMapView.generate(1)
      val charaView = GameCharacterOnFieldView.generate(1, (0 ,0))
      this.currentStage.addChild(background, mapView.getContainer(), charaView.getSprite())
      charaView.getSprite() :: this.currentDisplayObjects
      mapView.getContainer() :: this.currentDisplayObjects
      FieldEvent.addEvent(Thinking)
//      val hitObject = new Shape()
//      hitObject.graphics.beginFill("#000000").drawRect(0, 0, this.currentStage.getBounds().width, this.currentStage.getBounds().height)
//      this.currentStage.hitArea = hitObject
//      this.currentStage.addEventListener(
//        "click",
//        (e :Object) => {
//          val event = e.asInstanceOf[Event]
//          // TODO 移動キャンセルイベントの追加(残念ながらclick位置の判別は必要そう)
//          FieldEvent.showCurrentEvent() match {
//            case _ => true
//          }
//        },
//        false
//      )
      this.currentStage.update()
      Ticker.setFPS(30)
      Ticker.useRAF = true
      Ticker.addEventListener("tick", this.currentStage)
      true
    })
  }
}
