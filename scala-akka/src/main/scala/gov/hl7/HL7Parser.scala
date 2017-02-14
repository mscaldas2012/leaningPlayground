package gov.hl7

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, AllDeadLetters, DeadLetter, Props}
import ca.uhn.hl7v2.model.Message
import ca.uhn.hl7v2.model.v25.message.ORU_R01
import ca.uhn.hl7v2.parser.{CanonicalModelClassFactory, EncodingNotSupportedException, Parser, PipeParser}
import ca.uhn.hl7v2.util.Terser
import ca.uhn.hl7v2.validation.impl.ValidationContextFactory
import ca.uhn.hl7v2.{DefaultHapiContext, HL7Exception, HapiContext}
import gov.hl7.HL7Parser.{ParseDocument, ParsedMessage}

/**
  * Created by marcelo on 2/10/17.
  */

object HL7Parser {

  case class ParseDocument()
  case class ParsedMessage(msg:Message)
}

class HL7Parser(filename: String) extends Actor with ActorLogging {
  private val ctx = new DefaultHapiContext()

  def receive: Receive = processDocument()

  def processDocument(): Receive = {
    case ParseDocument => {
      try {
        log.info("reading file...")
        val source = scala.io.Source.fromFile(filename)
        var content = try source.mkString finally source.close()
        log.info("starting parse")

        // Create the MCF. We want all parsed messages to be for HL7 version 2.5,
        // despite what MSH-12 says.
        var mcf: CanonicalModelClassFactory = new CanonicalModelClassFactory("2.5")
        ctx.setModelClassFactory(mcf)
       // ctx.setValidationContext(ValidationContextFactory.defaultValidation())


        // Pass the MCF to the parser in its constructor
        var parser: PipeParser = ctx.getPipeParser
        //Example message de-identified do not pass validation
        parser.setValidationContext(ValidationContextFactory.noValidation())

        var hapiMsg = parser.parse(content)//.asInstanceOf[ORU_R01]
        sender ! ParsedMessage(hapiMsg)
        log.info("message returned")
      } catch {
        case e: EncodingNotSupportedException => {
          e.printStackTrace()
        }
        case e: HL7Exception => {
          e.printStackTrace()
        }
      }
    }
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
    val actor = system.actorOf(Props(new HL7Parser(file)))

    import akka.actor.AllDeadLetters
    system.eventStream.subscribe(actor, classOf[AllDeadLetters])

    implicit val timeout = Timeout(5 seconds)

    val future = actor ? ParseDocument
    future onComplete {
      case util.Success(ParsedMessage(msg)) => {
        val endTime = System.currentTimeMillis()
        //println("Hapi Message: " + msg)
        println("in process took " + (endTime - startTime) + " ms")
        var t23:Terser = new Terser(msg);
        var adtMsg:ORU_R01 = msg.asInstanceOf[ORU_R01]
        var reps = adtMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getOBSERVATIONReps();
        for (i <- 0 to reps-1) {
          println(i + ": " + t23.get("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION("+i+")/OBX-5"));
        }
         system.shutdown

      }
    }
    val endTime = System.currentTimeMillis()
    println("out process took " + (endTime - startTime) + " ms")

  }


}


