package org.ulpgc.dacd;

import org.ulpgc.dacd.controller.*;
import org.ulpgc.dacd.model.ComandSet;
import org.ulpgc.dacd.view.UserInterface;

import javax.jms.JMSException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws MyException, JMSException {

        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        SqliteStorage storage = new DataMartBuilder(args[1]);
        subscriber.receive(storage);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ComandSet comandSet = new ComandSet();
        UserInterface userInterface = new UserInterface(comandSet);
        userInterface.executeCommand(args[1]);
    }
}