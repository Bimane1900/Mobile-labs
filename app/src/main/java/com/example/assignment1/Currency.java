package com.example.assignment1;

public class Currency {
    String name;
    double rate;

    public Currency(String name, double rate){
        this.name = name;
        this.rate = rate;
    }

    public Currency(String name){
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
