package study.user

import scala.concurrent.ExecutionContext

import play.api.Environment
import play.api.data.Forms._
import play.api.data._
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

    Ok("registered")
  }

  def findAll: Action[AnyContent] = Action.async {
    userService.findAll map { users =>
      Ok(views.html.users(users))
    }
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
