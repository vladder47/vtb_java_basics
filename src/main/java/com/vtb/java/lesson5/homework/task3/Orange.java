package com.vtb.java.lesson5.homework.task3;

public class Orange extends Fruit {
    public Orange(String name) {
        super(name, 1.5f);
    }

    @Override
    public String toString() {
        return "Апельсин\tНазвание: " + super.getName() + "\tВес: " + super.getWeight();
    }
}
