package config;

import java.util.Optional;

public class Environment {










    public static String get()
    {
        String env =  System.getProperty("env");

        // if set to "", default to local
        return "".equals(env) ? "local" : env;

    }
}