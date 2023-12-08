package org.ulpgc.dacd.control;


import org.ulpgc.dacd.model.Weather;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                WeatherStore weatherStore = new JMSWeatherStore(args[1]);
                WeatherController weatherController = new WeatherController(new WeatherMapProvider(args[0]), weatherStore);
                try {
                    weatherController.execute();
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 6, TimeUnit.HOURS);
    }
}
