package study.user

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext }

import play.api.Environment
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.{ AbstractController, Action, AnyContent, ControllerComponents }

import javax.inject.{ Inject, Singleton }

@Singleton
class UserRoutes @Inject()(implicit ec: ExecutionContext,
  cc: ControllerComponents,
  userService: UserServiceImpl,
  env: Environment)
  extends AbstractController(cc) {

  import UserRoutes._

  def registered: Action[AnyContent] = Action { implicit request =>
    val user = userRegisteredForm.bindFromRequest.get

    userService.create(user.id, user.password)

    println(Await.result(userService.findAll(), Duration.Inf))

    Ok("registered")
  }
}

object UserRoutes {
  val userRegisteredForm = Form(
    mapping(
      "id" -> text,
      "password" -> text
    )(UserRegisteredFormDomain.apply)(UserRegisteredFormDomain.unapply)
  )

  case class UserRegisteredFormDomain(id: String, password: String)
}
