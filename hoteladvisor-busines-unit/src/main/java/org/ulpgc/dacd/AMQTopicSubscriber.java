package org.ulpgc.dacd;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashSet;
import java.util.Set;

public class AMQTopicSubscriber implements Subscriber{
    private final Connection connection;
    private final String clientId  ="event-store-builder2";;
    private final Session session;
    private Set<String> processedMessages = new HashSet<>();



    public AMQTopicSubscriber(String url) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    public void receive(String topicName,String topicName2,SqliteStorage storage) throws MyException {
        try {
            Topic destination = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicName);
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
        }try {
            Topic destination = session.createTopic(topicName2);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicName2);
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
        }catch (JMSException e) {
            throw new MyException("Error setting up MessageListener", e);
        }
    }
}