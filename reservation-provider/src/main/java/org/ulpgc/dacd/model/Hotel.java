package org.ulpgc.dacd.model;

import com.google.gson.JsonArray;

import java.time.Instant;
import java.util.List;

public class Hotel {

    private final JsonArray rates;
    private final Reservation reservation;

    public Hotel(JsonArray rates, Reservation reservation) {
        this.rates = rates;
        this.reservation = reservation;
    }


    public JsonArray getRates() {
        return rates;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
