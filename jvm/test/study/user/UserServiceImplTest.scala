package study.user

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import study.common.{ SequenceDao, SequenceService }
import study.test.DatabaseTest

class UserServiceImplTest extends PlaySpec with BeforeAndAfter with DatabaseTest {

  before {
    val dao = new UserDao(provider)

    import dao.dbConfig.db
    import dao.dbConfig.profile.api._
    import dao.users

    db run {
      users.delete
    }
  }

  after {
  }

  def service: UserService = {
    val sequenceDao = new SequenceDao(provider)

    val sequenceService = new SequenceService(sequenceDao)

    val dao = new UserDao(provider)

    new UserServiceImpl(dao, sequenceService)
  }

  "create(id: String, password: String)" should {
    "succeed" in {

      val beforeFindAll: Seq[User] = await(service.findAll())

      beforeFindAll.length === 0

      await(service.create("id", "pass"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length === 1
    }
  }

  "findAll()" should {
    "succeed" in {
      val result: Seq[User] = await(service.findAll())

      result.length === 0
    }
  }
}
