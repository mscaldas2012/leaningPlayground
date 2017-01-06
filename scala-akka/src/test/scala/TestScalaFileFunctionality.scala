import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by marcelo on 1/6/17.
  */
class TestScalaFileFunctionality extends FlatSpec with Matchers{


    "File " should " read easily " in {
      var startTime = System.currentTimeMillis()
      val source = scala.io.Source.fromFile("example.txt")
//      val content = try source.mkString finally source.close()
      var content = try source.getLines mkString "\n" finally source.close()


      var endTime = System.currentTimeMillis()
      println("method took " + (endTime - startTime) + " milliseconds")
      println(content)
      println(content.getClass())
      content should not be null
    }
}
