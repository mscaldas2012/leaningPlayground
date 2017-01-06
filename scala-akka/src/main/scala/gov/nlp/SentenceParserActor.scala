package gov.nlp

import java.io.{FileInputStream, IOException}

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Stash}
import gov.nlp.NameRecognitionActor.{NamesFoundMsg, ProcessTokens}
import gov.nlp.SentenceParserActor.{FeatureSet, StartProcessFileMsg}
import gov.nlp.TokenParserActor.{ProcessStringMsg, StringProcessedMsg}
import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.sentdetect.{SentenceDetectorME, SentenceModel}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}

import scala.collection.mutable.ArrayBuffer

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
  var tokenizer: TokenizerME = _

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

object NameRecognitionActor {

  case class ProcessTokens(tokens: Array[String])

  //Message to send the tokens back to caller Actor.
  case class NamesFoundMsg(names: ArrayBuffer[String])

}

class NameRecognitionActor extends Actor with ActorLogging {
  val modelIn = new FileInputStream("src/test/resources/en-ner-person.bin")
  val model = new TokenNameFinderModel(modelIn)
  val nameFinder = new NameFinderME(model)

  def receive = normalReceive

  def normalReceive: Receive = {
    case ProcessTokens(tokens) => {
      log.info("processing list of tokens")
      val names = nameFinder.find(tokens)
      val result = new ArrayBuffer[String]()
      for (name <- names) {
        for (idx <- name.getStart to name.getEnd-1) {
          result.append(tokens(idx))
        }
      }
      sender ! NamesFoundMsg(result)
    }
    case _ => log.error("NameRecognitionActor: message not recognized")
  }
}

// Sentence Parsers
object SentenceParserActor {

  //Message to process a given file...
  case class StartProcessFileMsg(words: Array[String])

  case class FeatureSet(tokens: ArrayBuffer[String], names: ArrayBuffer[String])

}

class SentenceParserActor(filename: String) extends Actor with ActorLogging {
  private var totalLines = 0
  private var linesProcessed = 0
  private var tokens: ArrayBuffer[String] = ArrayBuffer[String]()
  private var names: ArrayBuffer[String] = ArrayBuffer[String]()

  private var fileSender: Option[ActorRef] = None

  def receive = startProcess

  def startProcess: Receive = {
    case StartProcessFileMsg => {
      log.info("starting counter")
      context.become(processAlreadyRunning(sender))
      fileSender = Some(sender)

      val source = scala.io.Source.fromFile(filename)
      var content = try source.mkString finally source.close()

      val modelIn = new FileInputStream("src/test/resources/en-sent.bin")
      try {
        val model = new SentenceModel(modelIn)
        val sentenceDetector = new SentenceDetectorME(model)
        val sentences = sentenceDetector.sentDetect(content)
        for (s <- sentences) {
          context.actorOf(Props[TokenParserActor]) ! ProcessStringMsg(s)
          totalLines += 1
        }
      } catch {
        case e: IOException => {
          e.printStackTrace()
        }
      } finally if (modelIn != null)
        try
          modelIn.close()
        catch {
          case e: IOException => {
          }
        }

    }
    case _ => log.error("SentenceParserActor: Message not recognized.")
  }

  def processAlreadyRunning(respondTo: ActorRef): Receive = {
    case StringProcessedMsg(wordsAmount) => {
      log.info("processing msg returned... " + totalLines + " sentences...")
      tokens = tokens ++ wordsAmount
      //Extract named entities:
      context.actorOf(Props[NameRecognitionActor]) ! ProcessTokens(wordsAmount)
      //      if (linesProcessed == totalLines) {
      //        fileSender.map(_ ! tokens)
      //      }
    }
    case NamesFoundMsg(namesList) => {
      log.info("Received somenames...")
      names = names ++ namesList
      linesProcessed += 1
      if (linesProcessed == totalLines) {
         respondTo ! FeatureSet(tokens, names)
         //context stop self
      }
    }
    case _ => log.warning("Process is already running!")
  }
}


object Sample extends App {

  import akka.dispatch.ExecutionContexts._
  import akka.pattern.ask
  import akka.util.Timeout

  import scala.concurrent.duration._


  override def main(args: Array[String]) {
    runParser(args(0))
  }

  def runParser(file: String): Unit = {
    val startTime = System.currentTimeMillis()
    implicit val ec = global
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new SentenceParserActor(file)))
    implicit val timeout = Timeout(25 seconds)
    val future = actor ? StartProcessFileMsg
    future onComplete {
      case util.Success(FeatureSet(tokens, names)) => {
        println("tokens: " + tokens.length)
        println("names: " + names.length + " --> " + names )
        system.shutdown
      }
    }
    val endTime = System.currentTimeMillis()
    println("process took " + (endTime - startTime) + " ms")

  }

  //    future.map {
  //      tokens =>
  //      val r:ArrayBuffer[String] = tokens.asInstanceOf[ArrayBuffer[String]]
  //      println("Total number of words " + r.length)
  ////      system.shutdown
  //    }
  //    future.map { names =>
  //      println("found names: " + names)
  //      system.shutdown()
  //    }

}