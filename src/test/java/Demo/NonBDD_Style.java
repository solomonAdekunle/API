package Demo;

import GetCallSteps.TestBase;

import java.io.*;
import java.util.Properties;

public class NonBDD_Style  extends TestBase{
 static Properties prop = new Properties();
    public static Properties Resources= null;


    public static String setEnv(String env)throws IOException{
        prop= new Properties();
        FileInputStream fs;
        fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\config\\Config.Properties");
        try {
            prop.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.toString();
    }





    public static String getfile() throws IOException {
        Resources = new Properties();
        FileInputStream fs;
        fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\PreDefinedSchema.json");
        try {
            Resources.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Resources.toString();
    }
}