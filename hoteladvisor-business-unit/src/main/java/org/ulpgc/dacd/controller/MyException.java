package org.ulpgc.dacd.controller;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
    public MyException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

