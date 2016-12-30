package gov.nlp

import java.io.FileInputStream

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import gov.nlp.SentenceParserActor.StartProcessFileMsg
import gov.nlp.TokenParserActor.{ProcessStringMsg, StringProcessedMsg}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

/**
  * Created by marcelo on 12/30/16.
  */


//Token Parsers - per sentence
object TokenParserActor {

  //Message that receives a sentence to be processed
  case class ProcessStringMsg(string: String)
  //Message to send the tokens back to caller Actor.
  case class StringProcessedMsg(words: Array[String])


}

class TokenParserActor() extends Actor with ActorLogging with Stash {
  var tokenizer:TokenizerME = _

  //Trying to initialize model
  override def preStart(): Unit = {
    log.info("TokeParserActor.preStart...")
    val modelIn = new FileInputStream("src/test/resources/en-token.bin")
    val model = new TokenizerModel(modelIn)
    tokenizer = new TokenizerME(model)
    //self ! InitializationDone
  }

  def receive = normalReceive

  def initialReceive: Receive = {
    case InitializationDone => {
      log.info("initialization done!")
      context.become(normalReceive)
      //unstash()
    }
    case _ => stash()
  }

  def normalReceive: Receive = {
    case ProcessStringMsg(string) => {
      log.info("prosessing string msg")
      val tokens = tokenizer.tokenize(string)
      sender ! StringProcessedMsg(tokens)
    }
    case _ => log.error("TokenParserActor: message not recognized")
  }

  case class InitializationDone()

}

// Sentence Parsers
object SentenceParserActor {

  //Message to process a given file...
  case class StartProcessFileMsg(words: Array[String])

}

class SentenceParserActor(filename: String) extends Actor with ActorLogging {
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  def receive = startProcess

  def startProcess: Receive = {
    case StartProcessFileMsg => {
      log.info("starting counter")
      context.become(processAlreadyRunning)
      fileSender = Some(sender)

      import scala.io.Source._
      fromFile(filename).getLines.foreach { line =>
        context.actorOf(Props[TokenParserActor]) ! ProcessStringMsg(line)
        totalLines += 1
      }
    }
    case _ => log.error("SentenceParserActor: Message not recognized.")
  }

  def processAlreadyRunning: Receive = {
    case StringProcessedMsg(wordsAmount) => {
      log.info("processing msg returned...")
      result += wordsAmount.length
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.map(_ ! result)
      }
    }
    case _ => log.warning("Process is already running!")
  }
}


object Sample extends App  {

  import akka.dispatch.ExecutionContexts._
  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._


  override def main(args: Array[String]) {
    runParser(args(0))
  }

  def runParser(file: String): Unit = {
    implicit val ec = global
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new SentenceParserActor(file)))
    implicit val timeout = Timeout(25 seconds)
    val future = actor ? StartProcessFileMsg
    future.map { result =>
      println("Total number of words " + result)
      system.shutdown
    }
  }
}