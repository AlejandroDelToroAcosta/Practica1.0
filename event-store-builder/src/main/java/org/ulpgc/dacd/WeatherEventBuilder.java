package org.ulpgc.dacd;

import com.google.gson.*;
import org.ulpgc.dacd.model.Weather;

import javax.jms.MessageListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherEventBuilder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(formatter.format(src)))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, typeOfT, context) ->
                    Instant.from(formatter.parse(json.getAsString())))
            .create();


    public void buildEvent(List<Weather> weatherList) {

        if (weatherList.isEmpty()) {
            System.err.println("Weather list is empty. Nothing to write.");
            return;
        }
        Weather firstWeather = weatherList.get(0);
        String ts = firstWeather.getTs().toString().replace(":", "-");
        String directoryPath = "eventstore" + File.separator + "prediction.Weather" + File.separator + firstWeather.getSs() + File.separator + ts;
        String path = directoryPath + File.separator + ".events";

        System.out.println("WeatherlISRT" +weatherList);
        for (Weather weather : weatherList){
            write(weather, path);
        }
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());

            } else {
                System.out.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }
    }
    public void write(Weather weather, String path){


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json = gson.toJson(weather);
            writer.write(json);
            writer.newLine();
            writer.close();
            System.out.println("Weather data written to: " + path);
        } catch (IOException e) {
            System.out.println("Failed to write weather data to: " + path);
            e.printStackTrace();
        }
    }

}
