package org.ulpgc.dacd;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class FileEventStoreBuilder implements Listener {

    @Override
    public void hotelConsume(String message, String topicName) throws MyException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        JsonObject reservation = jsonObject.getAsJsonObject("reservation");

        String ss = reservation.get("ss").getAsString();
        String ts = reservation.get("ts").getAsString();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(ts);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = zonedDateTime.format(formatter);

        String directoryPath = "datalake"+"\\"+"eventstore" + "\\" + topicName+"\\"+ss;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory created");
        }

        String filePath = directoryPath + "\\" + formattedDate + ".events";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + "\n");
            System.out.println("Message appended to file: " + filePath);
        } catch (IOException e) {
            throw new MyException("Error writing to file", e);
        }
    }

    @Override
    public void weatherConsume(String message, String topicName) throws MyException {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

        String ss = jsonObject.get("ss").getAsString();
        String ts = jsonObject.get("ts").getAsString();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(ts);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = zonedDateTime.format(formatter);

        String directoryPath = "datalake"+"\\"+"eventstore" + "\\" + topicName+"\\"+ss;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Directory created");
        }

        String filePath = directoryPath + "\\" + formattedDate + ".events";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(message + "\n");
            System.out.println("Message appended to file: " + filePath);
        } catch (IOException e) {
            throw new MyException("Error writing to file", e);
        }
    }
}