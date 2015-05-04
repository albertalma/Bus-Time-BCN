package com.bustime.almacorp.bustime.model;

public class BusTime {

    private String name;
    private String time;

    public BusTime(String name, String time) {
        super();
        this.name = name;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

}
