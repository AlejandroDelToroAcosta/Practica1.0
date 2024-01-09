package org.ulpgc.dacd.controller;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashSet;
import java.util.Set;
public class AMQTopicSubscriber implements Subscriber {
    private final Connection connection;
    private final String clientID = "business-unit";
    private final static String weatherTopic = "prediction.Weather";
    private final static String hotelTopic = "reservation.Hotel";
    private final Session session;
    private Set<String> processedMessages = new HashSet<>();


    public AMQTopicSubscriber(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientID);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void receive(SqliteStorage storage) throws MyException {
        try {
            Topic destination = session.createTopic(weatherTopic);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientID + weatherTopic);
            consumer.setMessageListener(message -> {
                try {
                    String textMessage = ((TextMessage) message).getText();

                    if (processedMessages.add(textMessage)) {
                        storage.storeWeather(textMessage);
                        System.out.println("Message received:" + textMessage);
                    } else {

                    }
                } catch (JMSException e) {
                    try {
                        throw new MyException("Error receiving message", e);
                    } catch (MyException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } catch (JMSException e) {
            throw new MyException("Error setting up MessageListener", e);
        }
        try {
            Topic destination = session.createTopic(hotelTopic);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientID + hotelTopic);
            consumer.setMessageListener(message -> {
                try {
                    String textMessage = ((TextMessage) message).getText();

                    if (processedMessages.add(textMessage)) {
                        storage.storeHotel(textMessage);
                        System.out.println("Message received:" + textMessage);
                    } else {

                    }
                } catch (JMSException e) {
                    try {
                        throw new MyException("Error receiving message", e);
                    } catch (MyException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } catch (JMSException e) {
            throw new MyException("Error setting up MessageListener", e);
        }
    }
}