name := "Accounts"

version := "1.0"

scalaVersion := "2.11.8"

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "joda-time" % "joda-time" % "2.9.7"
libraryDependencies += "uk.camsw" % "cqrs_2.11" % "1.0-SNAPSHOT"
libraryDependencies += "uk.camsw" % "cqrs_2.11" % "1.0-SNAPSHOT" % "test" classifier "tests"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
libraryDependencies += "org.scala-lang" % "scala-pickling_2.11" % "0.9.1"
