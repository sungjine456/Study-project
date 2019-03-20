package study.user

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.Duration

import play.api.Application
import play.api.test.{ PlaySpecification, WithApplication }

class UserServiceImplTest extends PlaySpecification {
  def userService(implicit app: Application) = Application.instanceCache[UserServiceImpl].apply(app)

  def await(v: Future[User]): User = Await.result(v, Duration.Inf)

  "create(id: String, password: String)" should {
    "succeed" in new WithApplication() {
      val beforeFindAll: Seq[User] = await(userService.findAll())

      beforeFindAll.length === 0

      await(userService.create("id", "pass"))

      val afterFindAll: Seq[User] = await(userService.findAll())

      afterFindAll.length === 1
    }
  }

  "findAll()" should {
    "succeed" in new WithApplication() {
      val result: Seq[User] = await(userService.findAll())

      result.length === 0
    }
  }
}
