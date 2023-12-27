package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Weather {
    private static HttpURLConnection connection;
    private static BufferedReader reader;
    private static String line;
    private static StringBuilder content = new StringBuilder();
    public static void main(String[] args) {
        try {
            String city = "Tallinn";
            String apiAccessKey = "c9acb84362714bfbda769dd0ee6f8185";
            String sourceRequest = "https://api.openweathermap.org/data/2.5/weather?q=Tallinn&appid=c9acb84362714bfbda769dd0ee6f8185";
            URL url = new URL(sourceRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int status = connection.getResponseCode();

            System.out.println(status);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
            System.out.println(content.toString());
            Map<String, Object > respMap = jsonToMap (content.toString());
            // don't need to convert from string to map again and again
            Map<String, Object > mainMap = (Map<String, Object >)respMap.get("main");
            Map<String, Object > windMap = (Map<String, Object >)respMap.get("wind");

            double windSpeed = (double) windMap.get("speed");
            double windAngle = (double) windMap.get("deg");
            double temperature = Math.round((double) mainMap.get("temp") - 273.15);
            double pressure = (double) mainMap.get("pressure");

//            first get weather as list
//            List<Map<String, Object >> weather = (List<Map<String, Object>>) (respMap.get("weather"));


            System.out.println("Wind Speed: " + windSpeed);
            System.out.println("Wind Angle: " + windAngle);
            System.out.println("Temperature: " + temperature + "C");
            System.out.println("Pressure: " + pressure);


//            weather as list
//            System.out.println("Weather: "+ weather);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, Object> jsonToMap(String str) {

        Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        return map;
}}

