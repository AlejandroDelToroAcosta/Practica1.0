package org.ulpgc.dacd;

import org.ulpgc.dacd.model.Weather;

import java.util.ArrayList;
import java.util.List;

public interface MessageReceiver {
    List<Weather> receive();
}
