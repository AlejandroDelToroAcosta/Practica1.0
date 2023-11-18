package org.ulpgc.dacd.model;

import java.time.Instant;

public class Weather {
    private final int clouds;
    private final double wind;
    private final double temperature;
    private final int humidity;
    private final Instant instant;
    private final double pop;

    public Weather(int clouds, double wind, double temperature, int humidity, Instant instant, double pop) {
        this.clouds = clouds;
        this.wind = wind;
        this.temperature = temperature;
        this.humidity = humidity;
        this.instant = instant;
        this.pop = pop;
    }

    public int getClouds() {
        return clouds;
    }

    public double getWind() {
        return wind;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Instant getInstant() {
        return instant;
    }

    public double getPop() {
        return pop;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "clouds=" + clouds +
                ", wind=" + wind +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", instant=" + instant +
                ", pop=" + pop +
                '}';
    }
}
