import org.scalajs.sbtplugin.ScalaJSCrossVersion
import sbtcrossproject.CrossPlugin.autoImport.{ crossProject, CrossType }

scalaVersion in ThisBuild := "2.12.6"

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

lazy val crossType = CrossType.Full

lazy val rootProject = project.in(file("."))
  .aggregate(jvm, js)
  .settings(
    name := "study-project",
    run in Compile := (run in Compile in jvm).evaluated)

lazy val jvm = studyProject.jvm
  .enablePlugins(PlayScala)
  .settings(name := "study-jvm")
  .settings(
    scalaJSProjects := Seq(studyProject.js),
    libraryDependencies ++= Seq(
      guice,
      specs2 % Test,
      "org.postgresql" % "postgresql" % "42.2.5",
      "com.typesafe.play" %% "play-slick" % "4.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
      "org.scalatest" %% "scalatest" % "3.2.0-SNAP7" % "test",
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test")
  )

lazy val js = studyProject.js
  .enablePlugins(ScalaJSPlay)
  .settings(
      name := "study-js",
      parallelExecution := false,
      libraryDependencies ++= Seq(
        guice,
        "org.scalatest" %% "scalatest" % "3.2.0-SNAP7" % "test" cross ScalaJSCrossVersion.binary))

lazy val studyProject = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    name := "study-shared",
    unmanagedSourceDirectories in Compile :=
      Seq((scalaSource in Compile).value) ++
        crossType.sharedSrcDir(baseDirectory.value, "main"))
