package Demo;
import groovy.time.BaseDuration;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.internal.assertion.Assertion;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*;

import net.serenitybdd.junit.runners.SerenityRunner;
import org.apache.xerces.xs.StringList;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.hamcrest.Matchers.*;
import groovy.json.JsonOutput;
import io.restassured.internal.path.ObjectConverter;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;
import static io.restassured.path.json.JsonPath.*;
import static org.junit.Assert.assertTrue;


@RunWith(SerenityRunner.class)
public class firstlesson {


    //@Test
    public void testStatusCode() {
        given().
                get("https://jsonplaceholder.typicode.com/posts/3").then().statusCode(200).toString();
    }

    /* Verify a multply content*/
// @Test
    public void testHasItemFunction() {
        given().get("https://jsonplaceholder.typicode.com/posts").
                then().body("id", hasItems(1, 2, 3));

    }


    /* Verifying a single content using equal to*/
    //@Test
    public void EqualToFunction() {
        given().get("https://jsonplaceholder.typicode.com/posts/1").then().body("id", equalTo(1));
    }

    /* test xml resoponse for single content*/
    // @Test
    public void testSingleContent() {
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").then().body("CUSTOMER.LASTNAME", equalTo("Fuller"));

    }

    /* Verifying test xml response for multiple body content*/
    //@Test
    public void testMultipleContent() {
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
                then().
                body("CUSTOMER.ID", equalTo("10")).
                body("CUSTOMER.FIRSTNAME", equalTo("Sue"))
                .body("CUSTOMER.STREET", equalToIgnoringCase("135 Upland Pl."))
                .body("CUSTOMER.CITY", equalToIgnoringCase("Dallas"));


    }

    /* Verifying complete text in one go*/
    //@Test
    public void testCompleteTextInOneGo() {
        given().when().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").then()
                .body("CUSTOMER.text()", equalToIgnoringCase("10SUEFuller135 Upland Pl.Dallas"));
    }

    /* USING xpath can also be used to find values*/
// @Test
    public void testUsingXpath1() {
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").then()
                .body(hasXPath("/CUSTOMER/FIRSTNAME", containsString("Sue")));
    }

    /* Testing all parameters by using root features*/
    // @Test
    public void testWithRoot() {
        given().auth().basic("user", "password").when().
                get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").
                then().root("groups").body("searchTerm", hasItem("Bahco Cutters")).body("pageTitle", hasItems("Bahco Cutters | RS Components")).toString();


    }

    /* detaching the root path in between*/
    //@Test
    public void testDetachedRoot() {
        given().auth().basic("user", "password").when().
                get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").
                then().root("groups").body("searchTerm", hasItem("Bahco Cutters")).body("seoText", hasItem("This is a test SEO Text for Bahco Cutters")).
                body("pageTitle", hasItems("Bahco Cutters | RS Components")).toString();


    }


    /* test a post request */
    //@Test
    public void testPostReques() {
        given().header("AppKey", "Key-value").param("wfs_fn", "first")
                .param("wfs_ln", "last").param("wfs_email", "test@test.com")
                .when().post("https://api.fonts.com/rest/json/Accounts/").then().header("Content-Length", containsString("151"))
                .statusCode(equalTo(401));
    }


    /* To get all response as String*/
    //@Test
    public void testGetResponseAsString() {
        String responseAsString = given().auth().basic("user", "password")
                .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").path("groups.seoText").toString();
        System.out.println("My Response:\n\n\n" + responseAsString);
        responseAsString.equalsIgnoreCase("");

    }

    //@Test
    public void testGetAllResponseAsString() {
        String responseAsString = given().auth().basic("user", "password")
                .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").path("groups.pageTitle").toString();
        System.out.println("My Response:\n\n\n" + responseAsString);
        // responseAsString.trim().replaceAll("\\[|\\]", "").contains("Knipex Cutters | RS Components, Bahco Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components");
        //responseAsString.equalsIgnoreCase("Knipex Cutters | RS Components, Bahco Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components");
        responseAsString.contains("Bahco Cutters | RS Components, Knipex Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components");
        assertTrue(responseAsString.contains("Bahco Cutters | RS Components, Knipex Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components"));
    }

    /* To get all response as InputStream*/
    //@Test
    public void testGetResponseAsInputStream() throws IOException {
        InputStream stream = given().auth().basic("user", "password")
                .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").asInputStream();
        //  System.out.println("Stream length:"+stream.toString().length());
        stream.close();

    }

    /* To get all response as byteArray */
    //@Test
    public void testGetResonseAsByteArray() {
        byte[] byteArray = given().auth().basic("user", "password")
                .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").asByteArray();
        // System.out.println(byteArray.length);
    }

    /* Extract details using path*/
    //@Test
    public void testExtractDetailsUsingPath() {
        String href =
                when().get("https://jsonplaceholder.typicode.com/photos/1").then()
                        .contentType(ContentType.JSON).body("albumId", equalTo(1))
                        .extract().path("url");
        // System.out.println(href);
        when().get(href).then().statusCode(200);

    }

    /* Extract details using path in one line*/
    //@Test
    public void testExtractDetailsUsingPatInOneLineh() {
        String href1 =
                when().get("https://jsonplaceholder.typicode.com/photos/1").path("thumbnailUrl");

        //System.out.println(href1);
        when().get(href1).then().statusCode(200);

    }


    /* Extract details as response for further use */
    //@Test
    public void testExtractDetailsUsingResponse() {
        Response response = when().get("https://jsonplaceholder.typicode.com/photos/1").then()
                .extract().response();
        System.out.println("ContentType" + response.contentType());
        System.out.println("Href: " + response.path("url"));
        System.out.println("Status Code: " + response.statusCode());
        response.then().header("Content-Type", containsString("utf-8"));

    }

    /* To get all attributes as list */
    //@Test
    public void testGetResponseAsList() {
        String response = get("http://services.groupkt.com/country/search/search?text=lands").asString();
        List<String> ls = from(response).getList("RestResponse.result.name");
        System.out.println("ListSiize: " + ls.size());
        for (String country : ls) {
            if (country.equals("Solomon Islands"))
                System.out.println("found my place");

            //.assertTrue(country.equalsIgnoreCase("Solomon Islands"));
        }
    }


    /* Extracting details as String and fetching further details using JSONPath*/
    //@Test
    public void testJsonPath() {
        String responseAsString =
                when().
                        get("http://jsonplaceholder.typicode.com/photos").
                        then().extract().asString();
        List<Integer> albumIDs = from(responseAsString).get("id");

        System.out.println(albumIDs.size());


    }

    /* Extracting details as String and fetching further details using JSONPath8*/
    // @Test
    public void testJsonPath2() {
        String json =
                given().auth().basic("user", "password")
                        .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").
                        then().extract().asString();
        JsonPath jsonPath = new JsonPath(json).setRoot("groups");
        List<String> list = jsonPath.get("searchTerm");
        System.out.println(list.size());

    }


    /* To get Cookies */
    // @Test
    public void testCookies() {
        Response response = get("http://jsonplaceholder.typicode.com/photos");
        Map<String, String> cookies = response.cookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    /* To Get Details of Cookies*/
    //@Test
    public void testDetailedCookies() {
        Response response = get("http://jsonplaceholder.typicode.com/photos");
        Cookie a = response.getDetailedCookie("__cfduid");
        System.out.println("Detailed: " + a.hasExpiryDate());
        System.out.println("Detailed: " + a.getExpiryDate());
        System.out.println("Detailed: " + a.hasValue());
    }

    // @Test
    public void testSeoTextFeildWithJavascriptTag() {
        String response = given().auth().basic("user", "password")
                .when().get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_432193/language/en/v1").
                        then().extract().asString();
        //    response.then().body("groups.seoText",isEmptyString());
        JsonPath jsonPath = new JsonPath(response).setRoot("groups");
        List<String> list = jsonPath.get("seoText");
        System.out.println(list.size());
        Assert.assertTrue(list.get(0).toString().isEmpty());


    }

    /* Generally Connect used https request */
    //@Test
    public void testConnectRequest() {
        when().request("CONNECT", "https://api.fonts.com/rest/json/Accounts/").then().statusCode(400);
    }

    /* to get a Query parameter */
    //@Test
    public void testQueryParameter() {
        given().queryParam("A", "A val").
                queryParam("B", "B val").
                when().get("https://api.fonts.com/rest/json/Accounts/").then()
                .statusCode(400);
    }


    @Test
    public void testQueryParameter2() {
        given().pathParams("storeId", "GB_1").
                pathParams("categoryId", "PSF_437408").
                auth().basic("user", "password").
                when().get("http://10.251.26.107:8088/indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/en/v1")
                .then().statusCode(200);


    }


    /* In Post Request we can set form parameter
    *   meaning creating a new data request */
    // @Test
    public void testFormParameter() {
        given().formParam("A", "A val").
                formParam("B", "B val").
                when().post("https://api.fonts.com/rest/json/Accounts/").
                then().statusCode(400);
    }

    /**
     * To set parameters-
     * if Request is Get then param will be treated as QueryParameter
     * if request is POST then param will be treated as formParameter
     */
    //@Test
    public void testSetParameters() {
        given().param("A", "A-value").
                param("B", "B-value").
                when().get("https://api.fonts.com/rest/json/Accounts/").
                then().statusCode(400);
    }

    /* To set a Multiple value parameter
       we can pass list, Multiple values or no values in param

     */


    //@Test
    public void testSetMultipleValuesParameters() {
        List<String> list = new ArrayList<String>();
        list.add("one");
        list.add("two");
        given().
                param("A", "A-value").
                param("B").
                param("C", list).
                when().get("https://api.fonts.com/rest/json/Accounts/").
                then().statusCode(400);
    }

    /* To set path as parameters type 2 */

    // @Test
    public void testSetPathParameters() {
        given().
                pathParam("type", "json").
                pathParam("section", "Domains").
                when().
                post("https://api.fonts.com/rest/{type}/{section}").
                then().statusCode(400);
    }

    /* Cookies can be set in request */
    // @Test
    public void setCookiesInRequest() {
        given().
                cookies("__utmt", "1").
                when().get("http://www.webservicex.com/globalweather.asmx?op=GetCitiesByCountry").
                then().statusCode(200);
    }

    /* Multiple Cookies can be set in request param */
    //@Test
    public void testSetMultipleCookiesInRequest() {
        // to set detailed cookie
        given().cookie("key", "val1", "val2");// this will create two cookies key=val1; key=val2
        // to set detailed cookie
        Cookie cookie = new Cookie.Builder("some_cookies", "same_value").setSecured(true).setComment("some comment").build();
        given().cookie(cookie).when().get("/path").then().assertThat().body(equalTo("x"));

        //to set multple detailed cookies
        Cookie cookie1 = new Cookie.Builder("some_cookie", "same_value").setSecured(true).setComment("some comment").build();
        Cookie cookie2 = new Cookie.Builder("some_cookie", "same_value").setSecured(true).setComment("some comment").build();
        Cookies cookies = new Cookies(cookie1, cookie2);
        given().cookies(cookies).when().get("/cookie").then().assertThat().body(equalTo("x"));

    }

    /* we can pass single header, headers with multiple values and
       multiple headers
     */
    // @Test
    public void testSetHeader() {
        given().
                header("k", "v").
                header("k10", "val1", "val2", "val3").
                headers("K1", "v1", "k2", "v2").
                when().
                get("https://api.fonts.com/rest/json/Accounts/").
                then().statusCode(400);
    }

    /* we can set a contentType */
    //@Test
    public void testSetContentType() {
        given().
                contentType(ContentType.JSON).
                //contentType(ContentType.HTML).
                //contentType(ContentType.XML)
                contentType("application/json; charset=utf-8").
                when().
                get("https://api.fonts.com/rest/json/Accounts/").
                then().statusCode(400);

    }

    /* status code verification */
    // @Test
    public void testStatusInResponse() {
        // given().get("http://jsonplaceholder.typicode.com/photos/").then().assertThat().statusCode(200).log().all();
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").then().assertThat().statusLine(("HTTP/1.1 200 OK"));
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").then().assertThat().statusLine((containsString("OK")));
    }

    /* header Verification */
    //@Test
    public void testHeaderInResponse() {
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").then().log().headers();
        //      given().auth().basic("user", "password").
//               when()
//               .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").then().assertThat().header("Cache-Control", "max-age=86400");
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_437408/language/en/v1").then().assertThat().headers("Transfer-Encoding", "chunked", "X-Content-Type-Options", "nosniff", "Content-Type", containsString("json"));
    }

    /* body text verification*/
    // @Test
    public void testBodyInResponsAsString() {
        String responseString = get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/2").asString();
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/2").then().assertThat().body(equalTo(responseString));
    }


    /* body attribute verification using java 8 lambda expression*/
    //@Test
    public void testBodyParametersInResponse() {
        //this is pre java 8
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").
                then().body("groups.pageTitle", new ResponseAwareMatcher<Response>() {
            public Matcher<?> matcher(Response response) throws Exception {
                return hasItems("Bahco Cutters | RS Components");
            }
        });

        // with Java 8 lambda expression
        given().get("http://jsonplaceholder.typicode.com/photos/1").then().body("thumbnailUrl", response -> equalToIgnoringCase("http://placehold.it/150/92c952"));
        given().get("http://jsonplaceholder.typicode.com/photos/1").then().body("thumbnailUrl", response -> endsWith("92c952"));


//        given().auth().basic("user", "password").
//               when()
//               .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").
//              then().body("groups.pageTitle",response -> hasItems("[Knipex Cutters | RS Components, Bahco Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components]"));

    }


    /**
     * The Follwing methods are for perfomance testing
     **/

   /* response time in measurement */
    //@Test
    public void testResonpseTime() {
        long t = given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").getTime();
        System.out.println("Time(s):   " + t);


    }

    /* Asserting that response time is less than maximum required*/
    // @Test
    public void testResonpseTimeAssertion() {
        given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").then().time(lessThanOrEqualTo(2000l));
    }

    /* Verifying that categoryid with two or more dimensions return corect search term*/
    //@Test
    public void testAppliedDimensionWithMoreThanDimensionNumber() {
        String responseAsString =
                given().auth().basic("user", "password").
                        when()
                        .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").then().extract().asString();
        List<String> list = from(responseAsString).get("groups.dimensions[0]");
        Assert.assertTrue(list.get(0).toString().equalsIgnoreCase("4294966852"));
        Assert.assertTrue(list.get(1).equalsIgnoreCase("4294966997"));
    }

    /* how to ready a response as string from list of array */
   // @Test
    public void testGetSrringResponseFromAraayList() {
        String responseAsString =
                given().auth().basic("user", "password").
                        when()
                        .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").then().extract().asString();
        List<String> list = from(responseAsString).get("groups.heading");
        System.out.println(list.size());
        Assert.assertTrue(list.get(0).toString().equalsIgnoreCase("Bahco Cutters"));
    }

    /* how to verify all header content at a go */
    //@Test
    public void testAllContentInHeaderResponse(String HeaderFiled) {

        String responseAsString = given().auth().basic("user", "password").
                when()
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1").then().extract().headers().toString();
        //checkHeader


        ArrayList<String> list = new ArrayList<String>();
        for (String field : list) {


        }

    }

    /* Verifying the size of a response */
    // @Test
    public void testCheckTheSizeOrLimitOfResponse() {
        given().get("http://ergast.com/api/f1/2017/circuits.json").
                then().assertThat().body("MRData.limit", equalToIgnoringCase("30"))
                .body("MRData.CircuitTable.Circuits.circuitId", hasSize(20)).and().header("vary", equalTo("Accept-Encoding,User-Agent"));
    }


  //  @Test
    public void testGetAllResponseContainsInAnyOrder() {
        given().auth().basic("user", "password")
                .when().contentType(ContentType.JSON)
                .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1")
                .then().body("groups.pageTitle", containsInAnyOrder("Bahco Cutters | RS Components, Knipex Cutters | RS Components, Facom Cutters | RS Components, CK Cutters | RS Components, FUJIYA Cutters | RS Components")).toString();


    }
   // @Test
    public void testCheckTheSizeOrLimitOfResponse2() {
        given().get("http://ergast.com/api/f1/drivers.json?limit=3").
                then().assertThat().body("MRData.limit", equalToIgnoringCase("3")).and()
                .body("MRData.DriverTable.Drivers.driverId", hasSize(3)).and().body("MRData.DriverTable.Drivers.nationality",hasItems("Italian,British,British")).extract().contentType().toString();
//.and().body("MRData.DriverTable.Drivers.nationality",equalToIgnoringCase("Italian,British,British")).toString()

    }

  // @Test
    public void testCheckTheSizeOrLimitOfResponse3() {
        String respSting = given().get("http://ergast.com/api/f1/drivers.json?limit=3").path("MRData.DriverTable.Drivers.nationality").toString();
           respSting.equalsIgnoreCase("Italian,British,British");


    }
   /* Verifying if the response schema predefine existing schema */
  // @Test
   public void testResponseSchema(){
       given().auth().basic("user", "password")
               .when()
                 .get("http://10.251.26.107:8088/indexable-filter/storeId/GB_1/categoryId/PSF_438811/language/en/v1")
               .then().assertThat().body(matchesJsonSchemaInClasspath("PredefinedSchema"));
   }




}
























