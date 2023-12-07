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

            for (JsonElement element : weatherJsonArray) {
                JsonObject weather = element.getAsJsonObject();

                JsonObject main = weather.get("main").getAsJsonObject();
                Double temp = main.get("temp").getAsDouble();

                Double rain = weather.get("pop").getAsDouble();

                int humidity = main.get("humidity").getAsInt();

                JsonObject clouds = weather.get("clouds").getAsJsonObject();
                int all = clouds.get("all").getAsInt();

                JsonObject wind = weather.get("wind").getAsJsonObject();
                Double speed = wind.get("speed").getAsDouble();

                long ts = weather.get("dt").getAsLong();

                long unixTimestamp = ts;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);

                if (weatherInstant.equals(instant)) {
                    weatherObject = new Weather(location,temp, humidity, rain, all, speed, weatherInstant);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherObject;
    }
}
