package org.ulpgc.dacd;


import org.ulpgc.dacd.AMQTopicSubscriber;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException, MyException {
        String topicWeather = "prediction.Weather";
        String hotelTopic = "reservation.Hotel";
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(args[1]);
        subscriber.start(listener, topicWeather, hotelTopic);
    }
}