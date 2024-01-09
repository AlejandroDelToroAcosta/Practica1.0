package org.ulpgc.dacd.model;

public class Island {
    private String name;
    private String capital;

    public Island(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    @Override
    public String toString() {
        return  name ;

    }
}
