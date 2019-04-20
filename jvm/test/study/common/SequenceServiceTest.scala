package study.common

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{ PlaySpecification, WithApplication }
import play.api.{ Application, Configuration, Mode }

import com.typesafe.config.ConfigFactory

class SequenceServiceTest extends PlaySpecification {

  val application: Application = GuiceApplicationBuilder()
    .configure(Configuration(ConfigFactory.load("test.conf")))
    .in(Mode.Test)
    .build()

  "Sequence value" should {
    "increases" in new WithApplication(application) {
      val injector: Injector = app.injector

      implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

      val provider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]

      val dao = new SequenceDao(provider)

      val service = new SequenceService(dao)

      val result1: Long = await(service.nextValue("User"))

      result1 == 1L

      val result2: Long = await(service.nextValue("User"))

      result2 == 2L
    }
  }
}
