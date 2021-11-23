package com.example.swdevclass;

public class FitnessCenter {
    private double lat;
    private double log;
    private String manager;
    private String phonenumber;
    private String price;
    private String etc;
    private String time;
    private String name;
    private String event;
    private String address;


    FitnessCenter(){}
    FitnessCenter(double lat, double log, String manager, String phonenumber,String price, String etc, String time,
                  String name, String event, String address){
        this.lat = lat;
        this.log = log;
        this.manager = manager;
        this.phonenumber = phonenumber;
        this.price = price;
        this.etc = etc;
        this.time = time;
        this.name = name;
        this.event = event;
        this.address = address;
    }
    public double getLat(){
        return lat;
    }
    public double getLog(){
        return log;
    }
    public String getManager(){
        return manager;
    }
    public String getPhonenumber(){
        return phonenumber;
    }
    public String getPrice(){
        return price;
    }
    public String getEtc(){
        return etc;
    }
    public String getTime(){
        return time;
    }
    public String getEvent(){
        return event;
    }
    public String getAddress(){
        return address;
    }
    public String getName() {
        return name;
    }
}
