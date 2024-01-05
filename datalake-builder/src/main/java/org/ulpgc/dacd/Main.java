package org.ulpgc.dacd;

import javax.jms.JMSException;

public class Main {
    public static void main(String[] args) throws JMSException, MyException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        //Subscriber subscriber2 = new AMQTopicSubscriber(args[0]);
        Listener listener = new FileEventStoreBuilder();
        //Listener listener2 = new FileEventStoreBuilder(args[2]);
        subscriber.start(args[1],args[2], listener);
        //subscriber2.start(args[3], listener2);
    }
}