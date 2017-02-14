
name := "learn-akka"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.16"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.6.0"
libraryDependencies += "org.apache.opennlp" % "opennlp-uima" % "1.6.0"

libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % "3.7.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.7.0" classifier "models"
)



libraryDependencies += "ca.uhn.hapi" % "hapi-base" % "2.2"
libraryDependencies += "ca.uhn.hapi" % "hapi-structures-v231" % "2.2"
libraryDependencies += "ca.uhn.hapi" % "hapi-structures-v25" % "2.2"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.22"