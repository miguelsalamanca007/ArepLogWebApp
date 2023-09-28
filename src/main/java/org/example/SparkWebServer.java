package org.example;

import static spark.Spark.*;

public class SparkWebServer {

    private static int currentIndex = -1;

    private static String[] urls = {"http://logger-service1:4568/logString?value=", "http://logger-service2:4568/logString?value=", "http://logger-service3:4568/logString?value="};

    public static void main(String... args) {
        HttpRemoteCaller remoteCaller = new HttpRemoteCaller();
        port(getPort());
        staticFiles.location("/public");
        get("sendString", (req, res) -> {
            String value = req.queryParams("value").replace(" ", "%20");
            String url = getUrl();
            System.out.println("The current value is: " + value);
            System.out.println("The current url is: " + url+value);
            String response = remoteCaller.logString(url, value);
            return response;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static String getUrl(){
        int index = getNextNumber();
        return urls[index];
    }

    private static int getNextNumber(){
        currentIndex = (currentIndex + 1) % 3;
        return currentIndex;
    }

}


