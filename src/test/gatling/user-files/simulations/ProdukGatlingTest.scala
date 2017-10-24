import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Produk entity.
 */
class ProdukGatlingTest extends Simulation {

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

    val scn = scenario("Test the Produk entity")
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
            exec(http("Get all produks")
            .get("/ayoapp/api/produks")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new produk")
            .post("/ayoapp/api/produks")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "idProduk":"0", "kodeProduk":"SAMPLE_TEXT", "idKartu":"0", "denom":"0", "hpp":"0", "hargaMan":"0", "status":"0", "gangguan":"0", "idDistributor":null, "fisik":"0", "tglUpdate":"2020-01-01T00:00:00.000Z", "userUpdate":"SAMPLE_TEXT", "idDistributor2":null, "konversiSaldo":"0", "isreport":"0", "issplit":"0", "ototimeopen":"SAMPLE_TEXT", "ototimeclose":"SAMPLE_TEXT", "idDistributor3":null, "isstok":"0", "otoclosestatus":"0", "saldoMin":"0", "akses":"0", "hlr":"0", "isulang":"0", "isurut":"0", "formatppob":"0", "jenisppob":"0", "ketproduk":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_produk_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created produk")
                .get("/ayoapp${new_produk_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created produk")
            .delete("/ayoapp${new_produk_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) over (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
