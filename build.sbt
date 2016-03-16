name := "javacv-webcam"

organization := "com.chimpler"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-optimize", "-Xlint")

// fork a new JVM for 'run' and 'test:run'
fork := true

// add a JVM option to use when forking a JVM for 'run'
javaOptions += "-Xmx1G"

// Set the prompt (for this build) to include the project id.
//shellPrompt in ThisBuild := { state => "sbt:" + Project.extract(state).currentRef.project + "> "}