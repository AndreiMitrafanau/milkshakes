import java.io.File

import domain._
import org.scalatestplus.play.PlaySpec
import play.Environment
import service.ParseFileService

class ServiceTest extends PlaySpec {
  val env = Environment.simple()
  val file = new File(env.classLoader.getResource("input.txt").getFile)
  val service = new ParseFileService()

  "ParseFileService" should {
    "parse file" in {
      val result = service.parseFile(file)
      result mustBe List(TestCase(1, 5, List(
        Customer(0, 1, List(MilkShake(0, Malted))),
        Customer(1, 2, List(MilkShake(0, Unmalted), MilkShake(1, Unmalted))),
        Customer(2, 1, List(MilkShake(4, Unmalted))))),
        TestCase(2, 1, List(
          Customer(0, 1, List(MilkShake(0, Unmalted))),
          Customer(1, 1, List(MilkShake(0, Malted)))
        )))
    }
  }
}
