package com.example.pinpipo.healthy;

public class Weight {
    private String date;
    private  Integer weight;
    private  String status;

    public Weight(){

    }

    public Weight(String date, Integer weight, String status){
        this.date = date;
        this.weight = weight;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getStatus() {
        return status;
    }
}

