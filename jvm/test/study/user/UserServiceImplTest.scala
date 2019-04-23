package study.user

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }

import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.WithApplication
import play.api.{ Application, Configuration, Mode }

import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import study.common.{ SequenceDao, SequenceService }

class UserServiceImplTest extends PlaySpec with BeforeAndAfter {

  before {
    val injector: Injector = application.injector

    implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

    val provider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]

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

  def application: Application = GuiceApplicationBuilder()
    .configure(Configuration(ConfigFactory.load("test.conf")))
    .in(Mode.Test)
    .build()

  def getService(app: Application): UserService = {
    val injector: Injector = app.injector

    implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

    val provider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]

    val sequenceDao = new SequenceDao(provider)

    val sequenceService = new SequenceService(sequenceDao)

    val dao = new UserDao(provider)

    new UserServiceImpl(dao, sequenceService)
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

  "create(id: String, password: String)" should {
    "succeed" in new WithApplication(application) {

      val service: UserService = getService(app)

      val beforeFindAll: Seq[User] = await(service.findAll())

      beforeFindAll.length === 0

      await(service.create("id", "pass"))

      val afterFindAll: Seq[User] = await(service.findAll())

      afterFindAll.length === 1
    }
  }

  "findAll()" should {
    "succeed" in new WithApplication(application) {
      val service: UserService = getService(app)

      val result: Seq[User] = await(service.findAll())

      result.length === 0
    }
  }
}
