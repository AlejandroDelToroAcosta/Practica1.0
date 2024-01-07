package org.ulpgc.dacd;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.*;

public class DataMartBuilder implements SqliteStorage{

    private final String path;

    public DataMartBuilder(String path) {
        this.path = path;
    }

    @Override
    public void storeHotel(String message) {
        if (message != null) {
            try {
                Connection connection = DriverManager.getConnection(path);
                String createTableSQL = "CREATE TABLE IF NOT EXISTS hotels (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "code TEXT," +
                        "hotel_name TEXT," +
                        "rate INTEGER," +
                        "tax INTEGER," +
                        "check_in_date TEXT," +
                        "check_out_date TEXT," +
                        "reservation_name TEXT," +
                        "apikey TEXT," +
                        "ss TEXT," +
                        "timestamp TEXT," +
                        "island TEXT" +  // Nueva columna "Island"
                        ")";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);
                }

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                JsonArray ratesArray = jsonObject.getAsJsonArray("rates");

                if (ratesArray != null && !ratesArray.isJsonNull() && ratesArray.size() > 0) {
                    String reservationName = jsonObject.getAsJsonObject("reservation").get("name").getAsString();
                    String checkoutDate = jsonObject.getAsJsonObject("reservation").get("checkOut").getAsString();
                    String island = jsonObject.getAsJsonObject("reservation").get("island").getAsString();

                    String checkExistingRecordSQL = "SELECT * FROM hotels WHERE reservation_name = ? AND check_out_date = ?";
                    try (PreparedStatement checkExistingRecordStmt = connection.prepareStatement(checkExistingRecordSQL)) {
                        checkExistingRecordStmt.setString(1, reservationName);
                        checkExistingRecordStmt.setString(2, checkoutDate);
                        ResultSet resultSet = checkExistingRecordStmt.executeQuery();

                        if (resultSet.next()) {
                            System.out.println("El registro ya existe para el mismo reservation_name y check_out_date");
                        } else {
                            String insertDataSQL = "INSERT INTO hotels (code, hotel_name, rate, tax, check_in_date, check_out_date, reservation_name, apikey, ss, timestamp, island) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement insertDataStmt = connection.prepareStatement(insertDataSQL)) {
                                insertDataStmt.setString(1, ratesArray.get(0).getAsJsonObject().get("code").getAsString());
                                insertDataStmt.setString(2, ratesArray.get(0).getAsJsonObject().get("name").getAsString());
                                insertDataStmt.setInt(3, ratesArray.get(0).getAsJsonObject().get("rate").getAsInt());
                                insertDataStmt.setInt(4, ratesArray.get(0).getAsJsonObject().get("tax").getAsInt());
                                insertDataStmt.setString(5, jsonObject.getAsJsonObject("reservation").get("checkIn").getAsString());
                                insertDataStmt.setString(6, checkoutDate);
                                insertDataStmt.setString(7, reservationName);
                                insertDataStmt.setString(8, jsonObject.getAsJsonObject("reservation").get("apikey").getAsString());
                                insertDataStmt.setString(9, jsonObject.getAsJsonObject("reservation").get("ss").getAsString());
                                insertDataStmt.setString(10, jsonObject.getAsJsonObject("reservation").get("ts").getAsString());
                                insertDataStmt.setString(11, island);

                                insertDataStmt.executeUpdate();
                                System.out.println("Datos insertados");
                            }
                        }
                    }
                } else {
                    System.out.println("La lista de rates está vacía o es nula");
                }

                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void storeWeather(String message) {
        try {
            Connection connection = DriverManager.getConnection( path);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS weather (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "clouds INTEGER," +
                    "wind REAL," +
                    "temperature REAL," +
                    "humidity INTEGER," +
                    "instant TEXT," +
                    "pop REAL" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
            }

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(message, JsonObject.class);

            JsonObject locationObject = jsonObject.getAsJsonObject("location");
            String name = locationObject.get("name").getAsString();

            int clouds = jsonObject.get("clouds").getAsInt();
            double wind = jsonObject.get("windSpeed").getAsDouble();
            double temperature = jsonObject.get("temperature").getAsDouble();
            int humidity = jsonObject.get("humidity").getAsInt();
            String instant = jsonObject.get("predictionTs").getAsString();
            double pop = jsonObject.get("rain").getAsDouble();

            String checkInstantExistenceSQL = "SELECT instant FROM weather WHERE name=? AND instant=?";
            try (PreparedStatement checkInstantExistenceStmt = connection.prepareStatement(checkInstantExistenceSQL)) {
                checkInstantExistenceStmt.setString(1, name);
                checkInstantExistenceStmt.setString(2, instant);
                ResultSet resultSet = checkInstantExistenceStmt.executeQuery();

                if (!resultSet.next()) {
                    String insertWeatherSQL = "INSERT INTO weather (name, clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?,?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL)) {
                        preparedStatement.setString(1, name);
                        preparedStatement.setInt(2, clouds);
                        preparedStatement.setDouble(3, wind);
                        preparedStatement.setDouble(4, temperature);
                        preparedStatement.setInt(5, humidity);
                        preparedStatement.setString(6, instant);
                        preparedStatement.setDouble(7, pop);
                        preparedStatement.executeUpdate();
                    }
                } else {
                    String updateWeatherSQL = "UPDATE weather SET clouds=?, wind=?, temperature=?, humidity=?, pop=?  WHERE name=? AND instant=?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateWeatherSQL)) {
                        updateStatement.setInt(1, clouds);
                        updateStatement.setDouble(2, wind);
                        updateStatement.setDouble(3, temperature);
                        updateStatement.setInt(4, humidity);
                        updateStatement.setDouble(5, pop);
                        updateStatement.setString(6, name);
                        updateStatement.setString(7, instant);

                        updateStatement.executeUpdate();
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}