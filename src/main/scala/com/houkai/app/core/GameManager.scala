package com.houkai.app.core

import scala.scalajs.js
import com.scalawarrior.scalajs.createjs._
import org.scalajs.dom
import com.houkai.app.view._

object GameManager {
  private val loader: LoadQueue = new LoadQueue(false)
  private var currentStage: Stage = null

  def getImage(imageKey: String): Object = {
    this.loader.getResult(imageKey)
  }

  def setCurrentStage(canvas: dom.html.Canvas): Unit = {
    this.currentStage = new Stage(canvas)
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
      val charaView = new GameCharacterOnFieldView("character")
      val mapView = new FieldMapView("tile1", 10, 10)
      charaView.playAction("walk")
      this.currentStage.addChild(background, mapView.getContainer(), charaView.getSprite())
      this.currentStage.update()
      Ticker.setFPS(30)
      Ticker.useRAF = true
      Ticker.addEventListener("tick", this.currentStage)
      true
    })
  }
}
