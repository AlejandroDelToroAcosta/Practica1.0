package org.ulpgc.dacd;

public interface Subscriber {
    void receive(String topicname,String topicName2, SqliteStorage storage) throws MyException;
}
