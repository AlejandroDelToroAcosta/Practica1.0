package org.ulpgc.dacd.model;

import java.time.Instant;

public class Weather {
    private Location location;
    private Double temperature;
    private int humidity;
    private Double rain;
    private int clouds;
    private Double windSpeed;
    private Instant predictionTs;
    private final String ss;
    private final Instant ts;

    public Weather(Location location, Double temperature, int humidity, Double rain, int clouds, Double windSpeed, Instant predictionTs) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.predictionTs = predictionTs;
        this.ss = "prediction-provider";
        this.ts = Instant.now();
    }

    public Location getLocation() {
        return location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getRain() {
        return rain;
    }

    public int getClouds() {
        return clouds;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Instant getPredictionTs() {
        return predictionTs;
    }

    public String getSs() {
        return ss;
    }

    public Instant getTs() {
        return ts;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "location=" + location +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", rain=" + rain +
                ", clouds=" + clouds +
                ", windSpeed=" + windSpeed +
                ", predictionTs=" + predictionTs +
                ", ss='" + ss + '\'' +
                ", ts=" + ts +
                '}';
    }
}

