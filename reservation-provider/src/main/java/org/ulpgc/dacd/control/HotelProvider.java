package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Reservation;

import java.time.Instant;

public interface HotelProvider {
    Hotel getHotel(Reservation reservation);
}
