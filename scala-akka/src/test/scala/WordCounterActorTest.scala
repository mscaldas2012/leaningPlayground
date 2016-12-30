import akka.actor.{ActorSystem, Props}
import akka.dispatch.ExecutionContexts.global
import akka.pattern.ask
import akka.util.Timeout
import gov.nlp.{Sample, SentenceParserActor}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.duration._


/**
  * Created by marcelo on 12/29/16.
  */
class WordCounterActorTest extends FlatSpec with Matchers with ScalaFutures {

  "WordCount" should "return value of words counted" in {
    println("running test")
    implicit val defaultPatience =
      PatienceConfig(timeout = Span(250, Seconds), interval = Span(5000, Millis))

    implicit val ec = global
    val system = ActorSystem("System")
    val actor = system.actorOf(Props(new SentenceParserActor("uima.log")))
    implicit val timeout = Timeout(25 seconds)
    //val future = actor ? StartProcessFileMsg

    whenReady(actor ? gov.nlp.SentenceParserActor.StartProcessFileMsg) { result =>
      println("Total number of words " + result)
      result should be(548)

    }
  }


  "RunSampleApp" should "work as well" in {
    println("testing main app")
    val args:Array[String] = Array("uima.log")
   // Sample.main(args)
    gov.nlp.Sample.runParser("uima.log")
    args should be
  }
}
