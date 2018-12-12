package Demo;

import GetCallSteps.TestBase;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import java.util.*;
import org.hamcrest.Matcher;
import java.util.List;
import org.apache.commons.lang3.*;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;


public class ServiceCaller extends TestBase {

    private static String size;

    public static void testResponse(){
        given().get(TestBase.getBaseUrl()+"/f1/drivers.json");
    }
    public static void testResponseStatusCode(int statusCode){
      given().get(TestBase.getBaseUrl()+"/f1/2017/circuits.json").then().statusCode(statusCode).toString();

    }
    public static void testTotalResponseBodySize(String size){
        String resString= given().get(TestBase.getBaseUrl()+"/f1/2017/circuits.json").path("MRData.total");
        Assert.assertTrue(resString.equalsIgnoreCase(size));

    }

    public static void testListOfCountryWithinResponseBody(String countries){
    String resString= given().get(TestBase.getBaseUrl()+"/f1/drivers.json?limit=3").path("MRData.DriverTable.Drivers.nationality").toString();
    resString.equalsIgnoreCase(countries);

    }

    public static void testSetQueryParameter(String param){
        given().queryParam("limit",param).when().get(TestBase.getBaseUrl()+"/f1/drivers.json");
    }

    public static void testSetQueryParameterResponseStatusCode(String param,int statusCode) {
        String resString = given().contentType(ContentType.JSON).when().queryParam("limit", param).when().get(TestBase.getBaseUrl() + "/f1/drivers.json")
                .then().assertThat().body("MRData.limit", hasItem(param)).and().assertThat().statusCode(statusCode).toString();
      //  resString.equalsIgnoreCase()

        //.body(Matchers.equalTo(responseString))

    }
    }


