package org.ulpgc.dacd;

import javax.jms.JMSException;
import java.io.IOException;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
    public MyException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

