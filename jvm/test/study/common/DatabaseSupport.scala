package study.common

import play.api.db.Databases

trait DatabaseSupport {

  val database = Databases(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://localhost:5432/postgres",
    config = Map(
      "username" -> "test",
      "password" -> "study"
    )
  )
}
