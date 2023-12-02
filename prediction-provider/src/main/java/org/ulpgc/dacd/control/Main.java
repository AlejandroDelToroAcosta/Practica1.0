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
                WeatherStore weatherStore = new JMSWeatherStore(args[1],args[2]);
                WeatherProvider weatherProvider = new WeatherMapProvider(args[0]);

                WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);
                weatherController.execute();

            }
        }, 0, 6, TimeUnit.HOURS);
    }
}
