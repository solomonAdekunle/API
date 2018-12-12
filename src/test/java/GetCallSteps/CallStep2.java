package GetCallSteps;

import Demo.NonBDD_Style;
import Utillities.RestExtention;
import com.google.gson.JsonObject;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.junit.runner.RunWith;


import javax.annotation.Resource;
import javax.validation.constraints.AssertTrue;
import java.io.*;

import java.io.FileReader;
import java.util.*;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.reset;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
@RunWith(SerenityRunner.class)
public class CallStep2 {
    public static ResponseOptions <Response> response;
    private RestExtention restExtention= new RestExtention();

    public CallStep2() throws IOException {

    }


    @Given("^I perform pathParams operation (.*) authentication for an (.*)$")
    public void iPerformPathParamsOperationFor(String withOrWithout,String url, DataTable table) throws Throwable {
        List<List<String>> data = table.raw();
        //Set the path params
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("categoryId", data.get(1).get(0));
        pathParams.put("storeId", data.get(1).get(1));
        pathParams.put("language", data.get(1).get(2));


        //perform post operation
        if(withOrWithout.equalsIgnoreCase("with")) {
            response = RestExtention.SetPathParameters(url, pathParams);
        } else {
            response = RestExtention.SetPathParametersWithOutAuth(url, pathParams);
        }
    }

    @Then("^I verify response StatusCode is correct as (.*)$")
    public void iVerifyResponseStatusCodeisCorrectAs(String statusCode) {
        //response.statusLine().equalsIgnoreCase(statusCode);
       // System.out.println(response.statusLine());
        Assert.assertTrue(response.statusLine().equalsIgnoreCase(statusCode));
    }




    @And("^I verify cache-control has (.*)$")
    public void iVerifyCacheControlHasMaxAge(String maxAge) {
   response.getHeader("Cache-Control").equalsIgnoreCase(maxAge);
   System.out.println(response.getHeader("Cache-Control"));
   Assert.assertTrue(response.getHeader("Cache-Control").equalsIgnoreCase(maxAge));
    }

    @And("^I verify response time is less than (.*)$")
    public void iVerifyResponseTimeIsLessThanL(int responseTime)  {
       Assert.assertTrue(response.time()<=responseTime );

    }

    @And("^I verify applied dimension values are as expected$")
    public void iVerifyGroupsFieldsValuesAreAsExpected() {
        response.body().jsonPath().get("groups.dimension").toString().equalsIgnoreCase("[]");
        Assert.assertFalse(response.body().jsonPath().get("groups.dimension").toString().equalsIgnoreCase("[]"));
    }



    @And("^I verify response schema is as expected$")
    public void iVerifyResponseSchemaIsAsExpected() throws IOException {
        JsonObject actual   = RestExtention.getJsonValue (response.body().asString());
        JsonObject expected = RestExtention.getJsonValue(restExtention.getExpectedJson().toString());
        Assert.assertEquals(expected, actual);
   }

    @And("^I verify row (.*) for that category is not returned in response$")
    public void iVerifyRowMarkedRemovedForThatCategoryIsNotReturnedInResponse(){

    }


    @And("^I verify the (.*) field is empty$")
    public void iVerifyTheGroupsSeoTextFieldIsEmpty(String seoText) throws Throwable {
        Assert.assertTrue(response.body().jsonPath().get(seoText).toString().equalsIgnoreCase("[]"));
    }
    @Then("^I should see service returning more than one applied dimension within the xml file$")
    public void iShouldSeeServiceReturningMoreThanOneAppliedDimensionWithinTheXmlFile()  {
        List<String> list= response.body().jsonPath().get("groups.dimensions[1]");
        Assert.assertTrue(list.get(0).toString().equalsIgnoreCase("4294313976"));
        Assert.assertTrue(list.get(1).equalsIgnoreCase("4294964840"));

    }
}

