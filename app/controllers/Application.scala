package controllers

import com.google.inject.Inject
import play.api.mvc._
import service.{ParseFileService, PlanCalculator}

class Application @Inject()(service: ParseFileService, calc: PlanCalculator) extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

  def proceedFile = Action(parse.multipartFormData) { request =>
    request.body.file("input").map { input =>
      val file = input.ref.file
      if (file.length() == 0) {
        Redirect(routes.Application.index()).flashing("error" -> "Wow! Empty file! Please try another one!")
      } else {
        val plan = calc.getProductionPlan(service.parseFile(file))
        Ok(plan.mkString("\n"))
      }
    }.getOrElse {
      Redirect(routes.Application.index())flashing("error" -> "Something wrong happened!")
    }
  }
}