package com.example.pinpipo.healthy.sleep;

public class Sleep {
    private String date;
    private String time;
    private String resultTime;

    public Sleep() {
    }

    public Sleep(String date, String time, String resultTime) {
        this.date = date;
        this.time = time;
        this.resultTime = resultTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }
}
