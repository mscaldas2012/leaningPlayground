import gov.util.CSVReader
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by caldama on 2/16/17.
  */
class TestDataLoaders extends FlatSpec with Matchers{

  "with file " should "read data into neo4j" in {
    var reader: CSVReader = new CSVReader()
    var lines = reader.readFile("src/test/resources/sites-2.csv")
    for (row <- lines) {
      for ( col <- row)
        print(s"${col} --> ")
      println()
    }
  }

}
