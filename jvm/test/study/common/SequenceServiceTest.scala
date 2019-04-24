package study.common

import scala.language.postfixOps

import org.scalatestplus.play.PlaySpec
import study.test.DatabaseTest

class SequenceServiceTest extends PlaySpec with DatabaseTest {

  "Sequence value" should {
    "increases" in {
      val dao = new SequenceDao(provider)

      val service = new SequenceService(dao)

      val result1: Long = await(service.nextValue("User"))

      result1 === 1L

      val result2: Long = await(service.nextValue("User"))

      result2 === 2L
    }
  }
}
