package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {

    public WeatherProvider weatherProvider;

    public WeatherController(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public void execute() {

        Location elHierro = new Location(-16.25462, 28.46824, "Valverde");
        Location laPalma = new Location(-17.76421, 28.68351, " Santa Cruz de La Palma");
        Location laGomera = new Location(-17.11331, 28.09163, "San Sebastián de La Gomera");
        Location tenerife = new Location(-17.91578, 27.80628, "Santa Cruz deTenerife");
        Location granCanaria = new Location(-15.41343, 28.09973, "Las Palmas de Gran Canaria");
        Location fuerteventura = new Location(-13.86272, 28.50038, "Puerto del Rosario");
        Location lanzarote = new Location(-13.54769, 28.96302, "Arrecife");
        Location laGraciosa = new Location(-13.50341, 29.23147, "Caleta de Sebo");

        List<Location> locationList = List.of(elHierro, laPalma,laGomera,tenerife,granCanaria,
                fuerteventura,lanzarote, laGraciosa);

        ArrayList<Instant> instantList = new ArrayList<>();
        ArrayList<Weather> weatherArrayList = new ArrayList<>();

        createInstant(instantList);
        getWeatherCall(instantList, locationList, weatherArrayList);

    }
    public static ArrayList<Instant> createInstant(ArrayList<Instant> instants ){
        for (int i = 0; i < 5; i++) {
            LocalDate hoy = LocalDate.now();
            LocalTime hour = LocalTime.of(12, 0);
            LocalDateTime todayHour = LocalDateTime.of(hoy, hour);
            Instant instant = todayHour.toInstant(ZoneOffset.UTC);
            Instant instant1 = instant.plus(i, ChronoUnit.DAYS);
            instants.add(instant1);
        }
        return instants;
    }
    public static ArrayList<Weather> getWeatherCall(ArrayList<Instant> instantList, List<Location> locationList,
                                                    ArrayList<Weather> weatherArrayList) {
        WeatherProvider weatherProvider = new WeatherMapProvider();

        for (Location iteredLocation : locationList) {
            for (Instant iteredInstant : instantList) {
                Weather weather = weatherProvider.getWeather(iteredLocation, iteredInstant);
                if (weather != null) {
                    System.out.println("Weather for " + iteredLocation.getName() + " at " + iteredInstant + ":");
                    System.out.println(weather);
                    System.out.println("\n");
                } else {
                    System.out.println("No weather data found for " + iteredLocation.getName() + " at " + iteredInstant);
                }
                weatherArrayList.add(weather);
            }
        }
        return weatherArrayList;
    }
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(new WeatherMapProvider());
        weatherController.execute();
    }
}
