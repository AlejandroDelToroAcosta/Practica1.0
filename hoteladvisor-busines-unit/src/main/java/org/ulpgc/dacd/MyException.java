package org.ulpgc.dacd;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
    public MyException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

