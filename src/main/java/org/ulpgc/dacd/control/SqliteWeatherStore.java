package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {

    @Override
    public void save(Location location, Instant instant) {
        WeatherProvider weatherProvider = new WeatherMapProvider(WeatherMapProvider.getApiKey());
        Weather weather = weatherProvider.getWeather(location, instant);

        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/aadel/Desktop/Segundo/DACD/sqlite/db/weather_database.db");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS \"" + location.getName() + "\" " +
                        "(clouds INTEGER," +
                        " wind REAL," +
                        " temperature REAL," +
                        " humidity INTEGER," +
                        " instant TEXT," +
                        " pop REAL" +
                        ")";


                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);


                String checkInstantExistenceSQL = "SELECT instant FROM \"" + location.getName() + "\" WHERE instant=?";
                try (PreparedStatement checkInstantExistenceStmt = connection.prepareStatement(checkInstantExistenceSQL)) {
                    checkInstantExistenceStmt.setString(1, instant.toString());
                    ResultSet resultSet = checkInstantExistenceStmt.executeQuery();
                    if (!resultSet.next()) {
                        String insertWeatherSQL = "INSERT INTO \"" + location.getName() + "\" " +
                                "(clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL)) {
                            preparedStatement.setInt(1, weather.getClouds());
                            preparedStatement.setDouble(2, weather.getWind());
                            preparedStatement.setDouble(3, weather.getTemperature());
                            preparedStatement.setInt(4, weather.getHumidity());
                            preparedStatement.setString(5, instant.toString());
                            preparedStatement.setDouble(6, weather.getPop());
                            preparedStatement.executeUpdate();
                        }
                    } else {
                        String updateWeatherSQL = "UPDATE \"" + location.getName() + "\" SET " +
                                "clouds=?, wind=?, temperature=?, humidity=?, pop=? WHERE instant=?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateWeatherSQL)) {
                            updateStatement.setInt(1, weather.getClouds());
                            updateStatement.setDouble(2, weather.getWind());
                            updateStatement.setDouble(3, weather.getTemperature());
                            updateStatement.setInt(4, weather.getHumidity());
                            updateStatement.setDouble(5, weather.getPop());
                            updateStatement.setString(6, instant.toString());
                            updateStatement.executeUpdate();
                        }
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }
}
