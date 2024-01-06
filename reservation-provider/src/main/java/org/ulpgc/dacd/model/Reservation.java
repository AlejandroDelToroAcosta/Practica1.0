package org.ulpgc.dacd.model;

import java.time.Instant;

public class Reservation {
    private final String checkIn;
    private final String checkOut;

    private final String name;
    private final String apikey;

    private final String ss;
    private final Instant ts  ;
    private final String island  ;



    public Reservation(String checkIn, String checkOut, String name, String apikey,String island) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.apikey = apikey;
        this.ss = "reservation-provider";
        this.ts =Instant.now();
        this.island = island;
    }

    public String getSs() {
        return ss;
    }

    public Instant getTs() {
        return ts;
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
