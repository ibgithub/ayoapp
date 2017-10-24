import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the TransaksiPulsa entity.
 */
class TransaksiPulsaGatlingTest extends Simulation {

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

    val scn = scenario("Test the TransaksiPulsa entity")
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
            exec(http("Get all transaksiPulsas")
            .get("/ayoapp/api/transaksi-pulsas")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new transaksiPulsa")
            .post("/ayoapp/api/transaksi-pulsas")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "kodeProduk":"SAMPLE_TEXT", "hpTujuan":"SAMPLE_TEXT", "hpMember":"SAMPLE_TEXT", "hargaBeli":"0", "hpp":"0", "laba":"0", "com":"SAMPLE_TEXT", "admrpt":"0", "ulang":"0", "ulangTgl":"SAMPLE_TEXT", "fisik":"0", "manual":"0", "switch1":"0", "kodeGagal":"0", "waitSms":"0", "head2Head":"0", "hpPembeli":"SAMPLE_TEXT", "beaAdmin":"0", "isReport":"0", "suplierKe":"0", "idDistributor":null, "sn":"SAMPLE_TEXT", "ip":"SAMPLE_TEXT", "pesankirim":"SAMPLE_TEXT", "metode":"0", "toDistributor":"SAMPLE_TEXT", "idPortip":"0", "timeupdate":"2020-01-01T00:00:00.000Z", "idDistributorOld":null, "idDistributorProduk":null, "saldoSup":"0", "isrebate":"0", "enginename":"SAMPLE_TEXT", "typemsg":"0", "isro":"0"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_transaksiPulsa_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created transaksiPulsa")
                .get("/ayoapp${new_transaksiPulsa_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created transaksiPulsa")
            .delete("/ayoapp${new_transaksiPulsa_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
