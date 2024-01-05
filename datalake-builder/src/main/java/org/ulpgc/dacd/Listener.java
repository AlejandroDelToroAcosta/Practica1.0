package org.ulpgc.dacd;

public interface Listener {
    void hotelConsume(String message, String topicName) throws MyException;
    void weatherConsume(String message, String topicName) throws MyException;
}