package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherController {
    public ArrayList<Location> locationArrayList;
    public ArrayList<Instant> instantArrayList;

    public WeatherController(ArrayList<Location> locationArrayList, ArrayList<Instant> instantArrayList) {
        this.locationArrayList = locationArrayList;
        this.instantArrayList = instantArrayList;
    }

    public static void main(String[] args) {
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


        Instant instant = Instant.parse("2023-11-10T12:00:00Z");
        Instant instant2 = Instant.parse("2023-11-11T12:00:00Z");
        Instant instant3 = Instant.parse("2023-11-12T12:00:00Z");
        Instant instant4 = Instant.parse("2023-11-13T12:00:00Z");
        Instant instant5 = Instant.parse("2023-11-14T12:00:00Z");
        List<Instant> instantList = List.of(instant, instant2, instant3, instant4, instant5);

        WeatherMapProvider weatherMapProvider = new WeatherMapProvider();


        for (Location iteredLocation : locationList) {
            for (Instant iteredInstant : instantList) {
                Weather weather = weatherMapProvider.getWeather(iteredLocation, iteredInstant);
                if (weather != null) {
                    System.out.println("Weather for " + iteredLocation.getName() + " at " + instant + ":");
                    System.out.println(weather);
                    System.out.println("\n");
                } else {
                    System.out.println("No weather data found for " + iteredLocation.getName() + " at " + iteredInstant);
                }
            }
        }

    }
}
