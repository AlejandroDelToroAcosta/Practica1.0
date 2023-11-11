package org.ulpgc.dacd.control;


public class Main {
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(new WeatherMapProvider());
        weatherController.execute();
    }

}