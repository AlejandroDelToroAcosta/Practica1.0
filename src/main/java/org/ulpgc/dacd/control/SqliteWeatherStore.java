package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {
    @Override
    public void save(Weather weather) {

    }

    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new WeatherMapProvider();
        Weather weather = weatherProvider.getWeather(location, instant);
        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/aadel/Desktop/Segundo/DACD/sqlite/db/weather_database.db");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS weather (" +
                        "name TEXT ," +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "instant TEXT," +
                        "pop REAL" +
                        ")";
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);

                String insertWeatherSQL = "INSERT INTO weather (name, clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL);

                preparedStatement.setString(1, location.getName());
                preparedStatement.setInt(2, weather.getClouds());
                preparedStatement.setDouble(3, weather.getWind());
                preparedStatement.setDouble(4, weather.getTemperature());
                preparedStatement.setInt(5, weather.getHumidity());
                preparedStatement.setString(6, weather.getInstant().toString());
                preparedStatement.setDouble(7, weather.getPop());
                preparedStatement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }catch (NullPointerException exception){
                exception.printStackTrace();

            }
        } else {
            System.out.println("No weather data found for" + location.getName() + "at" + weather.getInstant());
        }

    }
}
