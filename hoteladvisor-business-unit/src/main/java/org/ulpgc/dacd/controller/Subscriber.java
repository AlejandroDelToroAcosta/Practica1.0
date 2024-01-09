package org.ulpgc.dacd.controller;

public interface Subscriber {
    void receive(SqliteStorage storage) throws MyException;
}
