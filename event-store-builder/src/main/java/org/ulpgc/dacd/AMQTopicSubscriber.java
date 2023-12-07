package org.ulpgc.dacd;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashSet;
import java.util.Set;

public class AMQTopicSubscriber implements Subscriber{
    private final Connection connection;
    private final String clientId = "event-store-builder";
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
    public void start(String topicName, Listener listener) {
        try {
            Topic destination = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(destination, clientId + topicName);
            consumer.setMessageListener(message -> {
                try {
                    String textMessage = ((TextMessage) message).getText();

                    if (processedMessages.add(textMessage)) {
                        listener.consume(textMessage);
                        System.out.println("Message received:" + textMessage);
                    } else {

                    }
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException("Error setting up MessageListener", e);
        }
    }
}