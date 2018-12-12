package Utillities;


import Demo.NonBDD_Style;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.Environment;
import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import org.apache.bcel.generic.NEW;
import org.apache.commons.io.IOUtils;
import org.junit.runner.Request;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import static io.restassured.RestAssured.given;
import org.apache.commons.lang3.StringUtils;


public class RestExtention {


    public static String env;
    //public static RequestSpecBuilder resp;
    public  static RequestSpecification Request;
    static Properties  prop= new Properties();







    public static RequestSpecBuilder rspec;
    //public static String pathUrl="/indexable-filter/storeId/{storeId}/categoryId/{categoryId}/language/{language}/v1";

    public static String setEnvironment(){
        return System.getProperty("env");
    }



   public String getBaseURL() {

        String url = System.getProperty("serviceUrl");
        if(StringUtils.isNotBlank(url)) {
            return url;
        }

        System.out.println("Using default service urls");
        switch (Environment.get()){
            case "local":
                return "http://localhost:8080";
            case "devci":
                return "http://10.251.26.107:8088";
            case "static1":
                return "http://brand-global-st1.ebs.ecomp.com:8087";
            case "static2":
                return "http://brand-global-st2.ebs.ecomp.com:8087";
            case "prod":
                return "http://brand-global.ebs.ecomp.com:8087";
            default:
                throw new RuntimeException("Unknown environment");
        }
    }



       public RestExtention() throws IOException {
//        rspec = new RequestSpecBuilder();
//        rspec.setBaseUri("http://10.251.26.107:8088");
//        rspec.setContentType(ContentType.JSON);
         RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("http://10.251.26.107:8088");
        builder.setContentType(ContentType.JSON);
        RequestSpecification resp = builder.build();
        Request= RestAssured.given().spec(resp);

        System.out.println("hello");


    }

    public static void GetOpsWithPathParameter(String url, Map<String,String> pathParams) throws URISyntaxException {
        Request.pathParams(pathParams);
        try {
            Request.get(new URI(url));
        } catch (URISyntaxException e){
            e.printStackTrace();

            }


    }

    public static ResponseOptions <Response> GetOps(String url){
        try {
          return Request.get(new URI(url));
        } catch (URISyntaxException e){
            e.printStackTrace();

        }
  return null;

    }


    public static Response SetPathParameters(String url, Map<String,String> pathParams){
                Request.pathParams(pathParams).auth().basic("user","password");
                   return Request.get((url));

    }
    public String getExpectedJson() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream fileInputStream = new FileInputStream(new File(classLoader.getResource("PredefinedSchema.json").getFile()));
        return IOUtils.toString(fileInputStream, "UTF-8");
    }

    public static JsonObject getJsonValue(String object){
        JsonParser parser = new JsonParser();
        return  (JsonObject) parser.parse(object);
    }

    public static Response SetPathParametersWithOutAuth(String url, Map<String,String> pathParams){
        Request.pathParams(pathParams);
        return Request.get((url));

    }

}
