package GetCallSteps;

import Demo.ServiceCaller;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class CallSteps {
    //private Response response;




        @Given("^I perform a GET operation for ergast api with statusCode as (.*)$")
        public void iPerformAGETOperationForErgastApi(int statusCode) {
            ServiceCaller.testResponseStatusCode(statusCode);

        }

    @Then("^I should see the total size return as (.*)$")
    public void iShouldSeeTheTotalSizeReturnAs(String size)  {
            ServiceCaller.testTotalResponseBodySize(size);

    }

    @Then("^I should see some of(.*)$")
    public void iShouldSeeSomeOfCountries(String countries)  {
            ServiceCaller.testListOfCountryWithinResponseBody(countries);

    }

    @Given("^I access ergast api$")
    public void iAccessErgastApi()  {
            ServiceCaller.testResponse();


    }


    @When("^I set query parameters for limit as (.*)$")
    public void henISetQueryParametersForLimitAs(String param)  {
            ServiceCaller.testSetQueryParameter(param);

    }



    @Then("^I should see query parameters for limit as (.*) and get status code (.*)$")
    public void iShouldSeeQueryParametersForLimitAsAndGetStatusCodeOk(String param,int statusCode)  {
            ServiceCaller.testSetQueryParameterResponseStatusCode(param,statusCode);

    }
}

