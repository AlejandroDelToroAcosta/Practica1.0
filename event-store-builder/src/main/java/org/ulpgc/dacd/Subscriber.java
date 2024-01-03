package org.ulpgc.dacd;

public interface Subscriber {
    void start(String topicname,String topicName2, Listener listener) throws MyException;

}
