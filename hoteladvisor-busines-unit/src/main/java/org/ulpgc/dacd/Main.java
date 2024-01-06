package org.ulpgc.dacd;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws MyException, JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        SqliteStorage storage = new DataMartBuilder(args[3]);
        subscriber.receive(args[1],args[2], storage);
    }
}