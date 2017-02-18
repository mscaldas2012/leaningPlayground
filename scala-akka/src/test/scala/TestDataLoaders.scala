import gov.util.CSVReader
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by caldama on 2/16/17.
  */
class TestDataLoaders extends FlatSpec with Matchers{

  "with file " should "read data into neo4j" in {
    var reader: CSVReader = new CSVReader()
    var lines = reader.readFile("src/test/resources/sitesCleanup", ",")
    for (row <- lines) {
      var previousCode:String = null
      var i = 0
      for ( col <- row) {
        val code1 = col.substring(0, 3)

        if (!code1.equals(previousCode)) {
          if (i > 0 ) { print(", ") }
          print(code1)
          i += 1
        }
        previousCode = code1
      }
      println()
    }
  }

}
