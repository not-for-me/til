name := "concurrency-examples"

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq (
  "Sonatype OSS Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at
    "https://oss.sonaytype.org/content/repositories/releases",
  "Typesafe Repository" at
    "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies += "commons-io" % "commons-io" % "2.4"

fork := true
