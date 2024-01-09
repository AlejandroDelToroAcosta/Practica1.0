package org.ulpgc.dacd;

public interface Subscriber {
    void start(Listener listener, String topicName, String topicname2) throws MyException;

}