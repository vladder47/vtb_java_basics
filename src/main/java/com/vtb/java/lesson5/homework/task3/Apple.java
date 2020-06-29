package com.vtb.java.lesson5.homework.task3;

public class Apple extends Fruit {
    public Apple() {
        super("Яблоко", 1.0f);
    }

    @Override
    public String toString() {
        return "Яблоко\tНазвание: " + this.name + "\tВес: " + this.weight;
    }
}
