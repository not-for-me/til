name := "simple-vocabulary-teacher"

version := "1.0"

scalaVersion := "2.11.7"

lazy val `simple-vocabuary-teacher` =
  (project in file(".")).enablePlugins(PlayScala)

routesGenerator := InjectedRoutesGenerator

com.typesafe.sbt.SbtScalariform.scalariformSettings

routesImport += "binders.PathBinders._"
