package com.vtb.java.lesson5.homework.task3;

public class Apple extends Fruit {
    public Apple(String name) {
        super(name, 1.0f);
    }

    @Override
    public String toString() {
        return "Яблоко\tНазвание: " + super.getName() + "\tВес: " + super.getWeight();
    }
}
