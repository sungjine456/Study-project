import sbtcrossproject.CrossPlugin.autoImport.{ crossProject, CrossType }

name := "StudyProject"

scalaVersion in ThisBuild := "2.12.6"

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

lazy val crossType = CrossType.Full

lazy val rootProject = project.in(file("."))
  .aggregate(jvm, js)
  .settings(run in Compile := (run in Compile in jvm).evaluated)

lazy val jvm = studyProject.jvm
  .enablePlugins(PlayScala)
  .settings(name := "Study JVM")
  .settings(
    scalaJSProjects := Seq(studyProject.js),
    libraryDependencies ++= Seq()
  )

lazy val js = studyProject.js
  .enablePlugins(ScalaJSPlay)
  .settings(name := "Study JS")

lazy val studyProject = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    name := "shared",
    unmanagedSourceDirectories in Compile :=
      Seq((scalaSource in Compile).value) ++
        crossType.sharedSrcDir(baseDirectory.value, "main"))
