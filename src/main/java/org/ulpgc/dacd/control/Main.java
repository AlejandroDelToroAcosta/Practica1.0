package org.ulpgc.dacd.control;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                WeatherController weatherController = new WeatherController(new WeatherMapProvider(args[0]));
                weatherController.execute();

            }
        }, 0, 6, TimeUnit.HOURS);
    }
}
