/**
  * Created by caldama on 12/28/16.
  */

import java.io.FileInputStream

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

case class ProcessStringMsg(string: String)

case class StringProcessedMsg(words: Array[String])

class StringCounterActor extends Actor {
  var modelIn = new FileInputStream("src/test/resources/en-token.bin")
  var model = new TokenizerModel(modelIn)
  var tokenizer = new TokenizerME(model)

  def receive = {
    case ProcessStringMsg(string) => {
      var tokens = tokenizer.tokenize(string)
      sender ! StringProcessedMsg(tokens)
    }
    case _ => println("Error: message not recognized")
  }
}

class WordCounterActor(filename: String) extends Actor {

  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  def receive = startProcess

  def startProcess: Receive = {
    case StartProcessFileMsg => {
      println("starting counter")
      context.become(processAlreadyRunning)
      fileSender = Some(sender)

      import scala.io.Source._
      fromFile(filename).getLines.foreach { line =>
        context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
        totalLines += 1
      }
    }
    case _ => println("WordCounterActor: Message not recognized.")
  }

  def processAlreadyRunning: Receive = {
    case StringProcessedMsg(wordsAmount) => {
      result += wordsAmount.length
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.map(_ ! result)
      }
    }
    case _ => println("Process is already running!")
  }
}

case object StartProcessFileMsg

object Sample extends App {

  import akka.dispatch.ExecutionContexts._
  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._


  override def main(args: Array[String]) {
    implicit val ec = global
    val system = ActorSystem("System")
    println("using file " + args(0))
    val actor = system.actorOf(Props(new WordCounterActor(args(0))))
    implicit val timeout = Timeout(25 seconds)
    val future = actor ? StartProcessFileMsg
    future.map { result =>
      println("Total number of words " + result)
      system.shutdown
    }
  }
}