package org.ulpgc.dacd.view;

import org.ulpgc.dacd.model.ComandSet;
public class UserInterface {
    private ComandSet comandSet;

    public UserInterface(ComandSet comandSet) {
        this.comandSet = comandSet;
    }

    public void executeCommand(String command) {
        comandSet.execute(command);
    }
}