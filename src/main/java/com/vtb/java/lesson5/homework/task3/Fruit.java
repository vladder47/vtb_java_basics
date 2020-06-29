package com.vtb.java.lesson5.homework.task3;

public abstract class Fruit {
    protected String name;
    protected float weight;

    public Fruit(String name, float weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
}
