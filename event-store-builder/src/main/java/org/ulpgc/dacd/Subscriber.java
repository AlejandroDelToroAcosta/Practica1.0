package org.ulpgc.dacd;

public interface Subscriber {
    void start(String topicname, Listener listener);

}
