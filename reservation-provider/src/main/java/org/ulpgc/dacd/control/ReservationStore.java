package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;

public interface ReservationStore {
    void save(Hotel hotel);
}
