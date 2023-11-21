package org.ulpgc.dacd.model;

public class Location {
    private final double lon;
    private final double lat;

    private final String name;

    public Location(double lon, double lat, String name) {
        this.lon = lon;
        this.lat = lat;
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public String getName() {
        return name;
    }
}
