package com.example.hopital1.model;

public class Groups {
    public String date;
    public int order;
    public String message;
    public String name;
    public String time;
    public String from;


    public Groups(String message, String date, String time, String name,String from ,int order) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.order=order;
        this.from=from;
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setGroupname(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }
}
