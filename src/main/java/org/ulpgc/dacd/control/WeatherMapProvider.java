package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.io.IOException;
import java.time.Instant;


public class WeatherMapProvider implements WeatherProvider {
    private static String API_KEY;

    public WeatherMapProvider(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    @Override
    public Weather getWeather(Location location, Instant instant) {
        Weather weatherObject = null;
        try {
            String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat="+ location.getLat()+
                    "&lon="+location.getLon()+
                    "&appid=" + API_KEY + "&units=metric";

            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(jsonString, JsonObject.class);
            JsonArray weatherJsonArray = json.getAsJsonObject().getAsJsonArray("list");

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
                if (weatherInstant.equals(instant)) {
                    weatherObject = new Weather(all, windSpeed, temperature, humidity, weatherInstant, pop);
                    break;
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherObject;
    }
}
