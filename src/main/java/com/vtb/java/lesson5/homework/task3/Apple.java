package com.vtb.java.lesson5.homework.task3;

public class Apple extends Fruit {
    private static final float weight = 1.0f;

    public Apple(String name) {
        super(name, weight);
    }

    @Override
    public String toString() {
        return "Яблоко\tНазвание: " + super.getName() + "\tВес: " + super.getWeight();
    }
}
