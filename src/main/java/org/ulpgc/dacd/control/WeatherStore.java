package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.time.Instant;

public interface WeatherStore {
    void save(Weather weather);
    void load(Location location, Instant instant);
}
