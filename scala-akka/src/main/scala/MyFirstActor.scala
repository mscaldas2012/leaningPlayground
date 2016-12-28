/**
  * Created by caldama on 12/28/16.
  */
import akka.actor.Actor


class MyFirstActor extends Actor {
    def receive = {
      case value: String => doSomething(value)
      case _ => println("received unknown message")
    }

    def doSomething(value:String) = {
      println("doing something... " + value)
    }
}
