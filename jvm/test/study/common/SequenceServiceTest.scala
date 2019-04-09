package study.common

import java.sql.ResultSet

import scala.language.postfixOps

import org.scalatest.{ BeforeAndAfter, FlatSpec }

class SequenceServiceTest extends FlatSpec with DatabaseSupport with BeforeAndAfter {

  private val connection = database.getConnection()

  before {
    val createSequenceTableQuery = """CREATE TABLE "Sequence" (
                                     "id"    VARCHAR(20) PRIMARY KEY NOT NULL,
                                     "value" NUMERIC                  NOT NULL
                                   );"""

    connection.prepareStatement(createSequenceTableQuery).execute()
  }

  after {
    val dropSequenceTableQuery = """DROP TABLE "Sequence""""
    connection.prepareStatement(dropSequenceTableQuery).execute()
  }

  val insertSequenceValueQuery = """INSERT INTO "Sequence" ("id", "value") VALUES ('User', 1);"""

  def updateSequenceValueQuery(value: Int) = s"""UPDATE "Sequence" SET VALUE = ($value + 1) WHERE "id" = "User";"""

  "test" should "h2 database" in {
    val connection = database.getConnection()

    connection.prepareStatement(insertSequenceValueQuery).execute()

    def selectQuery: ResultSet = connection.prepareStatement("select \"value\" from \"Sequence\" where \"id\" = 'User'").executeQuery()

    val result1 = selectQuery
    result1.next()
    result1.getInt("value") == 1

    connection.prepareStatement(updateSequenceValueQuery(1))

    val result2 = selectQuery
    result2.next()
    result2.getInt("value") == 2
  }
}
