import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Distributor entity.
 */
class DistributorGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the Distributor entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJSON
        .check(header.get("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all distributors")
            .get("/ayoapp/api/distributors")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new distributor")
            .post("/ayoapp/api/distributors")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "idDistributor":null, "nama":"SAMPLE_TEXT", "status":"0", "kodeId":"SAMPLE_TEXT", "pin":"SAMPLE_TEXT", "com":"SAMPLE_TEXT", "noKontak":"SAMPLE_TEXT", "metode":"SAMPLE_TEXT", "kodeParsing":"SAMPLE_TEXT", "kodeParsing2":"SAMPLE_TEXT", "replyno":"SAMPLE_TEXT", "tglInput":"2020-01-01T00:00:00.000Z", "userInput":"SAMPLE_TEXT", "tglUpdate":"2020-01-01T00:00:00.000Z", "userUpdate":"SAMPLE_TEXT", "ip":"SAMPLE_TEXT", "isvre":"0", "isgtw":"0", "ugtw":"SAMPLE_TEXT", "isfilter":"0", "parseSaldo":"SAMPLE_TEXT", "parseHarga":"SAMPLE_TEXT", "tiketWrap":"SAMPLE_TEXT", "istiketsend":"0", "pesanTiket":"SAMPLE_TEXT", "saldoSupwarn":"0", "issortby":"0", "parseUnit":"SAMPLE_TEXT", "isulangim":"0", "ishlr":"0", "kodeParsing3":"SAMPLE_TEXT", "idHistory":null, "kodeParsing4":"SAMPLE_TEXT", "selisihSupwarn":"0", "timeon":"SAMPLE_TEXT", "timeoff":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_distributor_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created distributor")
                .get("/ayoapp${new_distributor_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created distributor")
            .delete("/ayoapp${new_distributor_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
