package org.ulpgc.dacd;

public interface Listener {
    void consume(String message) throws MyException;
}
