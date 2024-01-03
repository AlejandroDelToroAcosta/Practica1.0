package org.ulpgc.dacd.model;

import java.time.Instant;

public class Reservation {
    private final String checkIn;
    private final String checkOut;

    private final String name;
    private final String apikey;

    public Reservation(String checkIn, String checkOut, String name, String apikey) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.apikey = apikey;
    }

    public String getApikey() {
        return apikey;
    }

    public String getName() {
        return name;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
}
