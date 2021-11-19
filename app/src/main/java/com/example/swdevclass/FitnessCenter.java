package com.example.swdevclass;

public class FitnessCenter {
    public String explanation;
    public double lat;
    public double log;
    private String manager;
    public String name;
    public String phonenumber;


    FitnessCenter(){}
    FitnessCenter(String explain, double lat, double log, String name, String phonenumber, String manager){
        this.explanation = explain;
        this.lat = lat;
        this.log = log;
        this.name = name;
        this.phonenumber = phonenumber;
        this.manager = manager;
    }

    public String getName() {
        return name;
    }
}
