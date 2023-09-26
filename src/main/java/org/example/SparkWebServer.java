package org.example;

import static spark.Spark.port;
import static spark.Spark.*;
import org.example.WebServices;


public class SparkWebServer {

    public static void main(String... args) {
        WebServices webServices = new WebServices();
        HttpRemoteCaller remoteCaller = new HttpRemoteCaller();
        port(getPort());
        staticFiles.location("/public");
        get("sendString", (req, res) -> {
            String value = req.queryParams("value");
            String response = remoteCaller.logString(value);
            return response;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}


