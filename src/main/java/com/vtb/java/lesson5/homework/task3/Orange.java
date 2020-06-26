package com.vtb.java.lesson5.homework.task3;

public class Orange extends Fruit {
    private static final float weight = 1.5f;

    public Orange(String name) {
        super(name, weight);
    }

    @Override
    public String toString() {
        return "Апельсин\tНазвание: " + super.getName() + "\tВес: " + super.getWeight();
    }
}
