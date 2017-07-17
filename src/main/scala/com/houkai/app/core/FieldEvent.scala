package com.houkai.app.core

sealed abstract class EventType
case object Thinking extends EventType
case object BeforeMoving extends EventType
case object AfterMoving extends EventType

object FieldEvent {
  private var eventStack : List[EventType] = Nil
  def addEvent(e: EventType): Unit = {
    this.eventStack = e :: this.eventStack
  }
  def removeCurrentEvent(): EventType = {
    eventStack.drop(1)
    eventStack.head
  }
  def showCurrentEvent():  EventType = {
    eventStack.head
  }
}
