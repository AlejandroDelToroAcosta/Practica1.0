package org.ulpgc.dacd;

import org.ulpgc.dacd.model.Weather;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


            WeatherMessageReceiver messageReceiver = new WeatherMessageReceiver(args[0], args[1]);
            messageReceiver.receive();
            WeatherEventBuilder weatherEventBuilder = new WeatherEventBuilder();
            weatherEventBuilder.buildEvent(messageReceiver.receive());


    }
}
