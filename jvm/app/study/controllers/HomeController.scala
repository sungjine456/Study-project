package study.controllers

import javax.inject._
import play.api.mvc._

import study.MyLibrary

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    val library = new MyLibrary

    Ok(views.html.index(library.value.toString))
  }
}
