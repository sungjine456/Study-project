package study.controllers

import scala.concurrent.ExecutionContext

import play.api.Environment
import play.api.mvc._

import javax.inject._
import study.user.UserServiceImpl

@Singleton
class HomeController @Inject()(implicit ec: ExecutionContext,
  cc: ControllerComponents,
  userService: UserServiceImpl,
  env: Environment)
  extends AbstractController(cc) {

  def index: Action[AnyContent] = Action async {
    userService.findAll() map { users =>
      Ok(views.html.index(users.length.toString))
    }
  }
}
