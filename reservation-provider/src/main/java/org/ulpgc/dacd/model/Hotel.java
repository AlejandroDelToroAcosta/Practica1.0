package org.ulpgc.dacd.model;

import com.google.gson.JsonArray;

import java.time.Instant;
import java.util.List;

public class Hotel {
    private static String ss = "reservation-provider";
    private static Instant ts = Instant.now() ;

    private final JsonArray rates;
    private final Reservation reservation;

    public Hotel(JsonArray rates, Reservation reservation) {
        this.rates = rates;
        this.reservation = reservation;
    }

    public static String getSs() {
        return ss;
    }

    public static Instant getTs() {
        return ts;
    }

    public JsonArray getRates() {
        return rates;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
