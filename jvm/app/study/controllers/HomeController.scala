package study.controllers

import scala.concurrent.ExecutionContext

import play.api.Environment
import play.api.mvc._

import javax.inject._

@Singleton
class HomeController @Inject()(implicit ec: ExecutionContext,
  cc: ControllerComponents,
  env: Environment)
  extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    val title = "Study Project"

    Ok(views.html.index(title))
  }
}
