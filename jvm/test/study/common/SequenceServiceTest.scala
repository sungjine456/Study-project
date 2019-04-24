package study.common

import scala.language.postfixOps

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import study.test.DatabaseTest

class SequenceServiceTest extends PlaySpec with BeforeAndAfter with DatabaseTest {

  val dao = new SequenceDao(provider)

  val service = new SequenceService(dao)

  before {
    import dao.dbConfig.db
    import dao.dbConfig.profile.api._
    import dao.sequences

    db run {
      sequences.filter(_.id === "User").map(_.value).update(0)
    }
  }

  "Sequence value" should {
    "increases" in {
      val result1: Long = await(service.nextValue("User"))

      result1 mustBe 0L

      val result2: Long = await(service.nextValue("User"))

      result2 mustBe 1L
    }
  }
}
