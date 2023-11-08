package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.ulpgc.dacd.model.Weather;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=28.41&lon=-17.45&appid=7e7cbb9533f4457ccb9a16973f9dcb7a&units=metric";
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(jsonString, JsonObject.class);
            JsonArray weatherJsonArray = json.getAsJsonObject().getAsJsonArray("list");

            List<Weather> weatherList = new ArrayList<>();

            for (JsonElement weather : weatherJsonArray) {
                JsonObject weatherJsonObject = weather.getAsJsonObject();

                JsonObject main = weatherJsonObject.getAsJsonObject("main");
                JsonObject clouds = weatherJsonObject.getAsJsonObject("clouds");
                JsonObject wind = weatherJsonObject.getAsJsonObject("wind");

                double temperature = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                double pop = weatherJsonObject.get("pop").getAsDouble();
                int dt = weatherJsonObject.get("dt").getAsInt();
                int all = clouds.get("all").getAsInt();
                double windSpeed = wind.get("speed").getAsDouble();

                long unixTimestamp = dt;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);

                Weather weatherObject = new Weather(all, windSpeed, temperature, humidity, weatherInstant, pop);
                weatherList.add(weatherObject);
            }
            for (Weather weatherIter : weatherList) {
                System.out.println("Temp:"+weatherIter.getTemperature()+"\n");
                System.out.println("clouds:"+weatherIter.getClouds()+"\n");
                System.out.println("wind:"+weatherIter.getWind()+"\n");
                System.out.println("humidity:"+weatherIter.getHumidity()+"\n");
                System.out.println("pop:"+weatherIter.getPop()+"\n");
                System.out.println("dt:"+weatherIter.getInstant()+"\n");
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}