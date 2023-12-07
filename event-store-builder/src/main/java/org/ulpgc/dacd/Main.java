package org.ulpgc.dacd;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder(args[2]);
        subscriber.start(args[1], listener);
    }
}
