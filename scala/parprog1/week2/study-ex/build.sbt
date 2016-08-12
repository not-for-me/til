scalaVersion := "2.11.5"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-optimise",
  "-Yinline-warnings"
)

fork := true

javaOptions += "-Xmx3G"

parallelExecution in Test := false

// grading libraries
libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter-core" % "0.6",
  "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
  "org.scalactic" %% "scalactic" % "2.2.6",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

dependencyOverrides += "org.scala-lang" % "scala-library" % scalaVersion.value


