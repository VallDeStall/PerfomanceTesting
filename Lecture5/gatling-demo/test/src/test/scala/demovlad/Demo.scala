package demovlad

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Demo extends Simulation {

  val httpProtocol = http
    .baseUrl("https://demo.nopcommerce.com")
    .acceptHeader("image/webp,*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0")
    .disableFollowRedirect

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_7 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
    "DNT" -> "1",
    "Pragma" -> "no-cache",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_8 = Map(
    "DNT" -> "1",
    "Pragma" -> "no-cache")

  val headers_9 = Map(
    "Accept" -> "*/*",
    "Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
    "Origin" -> "https://demo.nopcommerce.com",
    "X-Requested-With" -> "XMLHttpRequest")


  val scn = scenario("Demo")
    .exec(http("browse page")
      .get("/").check(status.is(200))
      .headers(headers_0))
    .pause(2)

    .exec(http("search product by key")
      .get("/search?q=book+").check(substring("book"))
      .headers(headers_0))
    .pause(2)

    .exec(http("choice the product")
      .get("/apple-macbook-pro-13-inch").check(status.not(404), status.not(500))
      .headers(headers_0))
    .pause(2)

    .exec(http("add product to cart")
      .post("/addproducttocart/details/4/1").check(status.is(200))
      .headers(headers_9))
    .pause(2)

    .exec(http("cart page")
      .get("/cart").check(substring("cart"))
      .headers(headers_0))

  setUp(scn.inject(atOnceUsers(1)))
    .assertions(global.successfulRequests.percent.gt(99))
    .protocols(httpProtocol)
}