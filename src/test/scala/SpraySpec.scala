import org.scalatest.{FlatSpec, ShouldMatchers}
import spray.http.HttpHeaders.RawHeader
import spray.routing.Directives._
import spray.testkit.ScalatestRouteTest
import spray.http.StatusCodes._
import spray.routing.HttpService.sealRoute


class SpraySpec extends FlatSpec with ScalatestRouteTest with ShouldMatchers {
    def route =
        get {
            path("hi") {
                complete("hello")
            } ~ path("genchars") {
                get {
                    headerValueByName("x-gen") { v =>
                        complete(v)
                    }
                }
            }
        } ~ post {
            path("ping") {
                complete("pong")
            }
        }

    "rest service" should "return hello on get" in {
        Get("/hi") ~> route ~> check {
            responseAs[String] shouldEqual "hello"
        }

    }
    it should "return 404 on unmatched path" in {
        Post("/") ~> sealRoute(route) ~> check {
            status shouldEqual NotFound
        }
    }

    it should "return pong on post to /ping" in {
        Post("/ping") ~> route ~> check {
            responseAs[String] shouldEqual "pong"
        }
    }

    it should "return value passed in x-gen header" in {
        val h = RawHeader("x-gen", "300")
        Get("/genchars").withHeaders(h) ~> route ~> check {
            responseAs[String] shouldEqual "300"
        }
    }

    it should "return 400 on missing header x-gen" in {
        Get("/genchars") ~> sealRoute(route) ~> check {
            status shouldEqual BadRequest
        }
    }

}
