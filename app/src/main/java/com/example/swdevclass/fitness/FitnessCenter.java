package com.example.swdevclass.fitness;

public class FitnessCenter {
    private double latitude;
    private double longitude;
    private String manager;
    private String phonenumber;
    private String price;
    private String etc;
    private String time;
    private String name;
    private String event;
    private String address;


    public FitnessCenter(){}
    public FitnessCenter(double latitude, double longitude, String manager, String phonenumber, String price, String etc, String time,
                  String name, String event, String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.manager = manager;
        this.phonenumber = phonenumber;
        this.price = price;
        this.etc = etc;
        this.time = time;
        this.name = name;
        this.event = event;
        this.address = address;
    }
    public void editFitnessCenter(String address, String phonenumber, String price, String time, String event, String etc){
        this.address = address; this.phonenumber = phonenumber;
        this.price = price; this.time = time;
        this.event = event; this.etc = etc;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
