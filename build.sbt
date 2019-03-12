name := "StudyProject"

lazy val rootProject = project.in(file("."))
  .aggregate(jvm, js, shared)
  .dependsOn(jvm, js, shared)

lazy val jvm = project.in(file("jvm"))
  .settings(name := "Study JVM")
  .dependsOn(shared)

lazy val js = project.in(file("js"))
  .settings(name := "Study JS"  )
  .dependsOn(shared)

lazy val shared = project.in(file("shared"))
  .settings()
