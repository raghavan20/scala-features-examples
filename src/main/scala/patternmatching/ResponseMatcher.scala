package patternmatching

import spray.http.HttpHeaders.`Content-Length`
import spray.http.{HttpEntity, _}
import HttpProtocols.`HTTP/1.1`
import StatusCodes._

object ResponseMatcher extends App {

    val r1 = HttpResponse(StatusCodes.Forbidden, entity = "not allowed", headers = List(), protocol = `HTTP/1.1`)
    val r2 = HttpResponse(OK, "hello", List(`Content-Length`(300)), `HTTP/1.1`)
    val r3 = HttpResponse(Unauthorized, "unauthorized")

    val responses = List(r1, r2, r3)

    responses.foreach(r => r match {
        case r@HttpResponse(Forbidden, _, _, _) => println(s"$r - forbidden")
        case r@HttpResponse(_, _, List(`Content-Length`(x)), _) => println(s"$r - content length: $x")
        case r:Any => println(s"$r - unmatched")
    })
}
