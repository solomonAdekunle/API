package GetCallSteps;

import Utillities.RestExtention;
import cucumber.api.java.Before;
import org.junit.BeforeClass;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestBase {


    @Before
    public static String getBaseUrl(){
            return "http://ergast.com/api";


    }
   @Before
    public void TestSetup() throws IOException {
        RestExtention restExtention= new RestExtention();

    }

}
