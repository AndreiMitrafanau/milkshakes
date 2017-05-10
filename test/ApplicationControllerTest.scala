import controllers.Application
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._
import service.{ParseFileService, PlanCalculator}

import scala.concurrent.Future


class ApplicationControllerTest extends PlaySpec with Results with OneAppPerSuite {
  private val controller = new Application(new ParseFileService(), new PlanCalculator())

  "index" should {
    "contain message" in {
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      val stat = status(result)
      stat mustBe 200
      bodyText contains "Please specify an input file"
    }
  }
}