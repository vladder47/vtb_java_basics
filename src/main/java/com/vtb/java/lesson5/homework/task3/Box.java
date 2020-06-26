package com.vtb.java.lesson5.homework.task3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box() {
        this.fruits = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public void addFruits(ArrayList<T> frs) {
        fruits.addAll(frs);
    }

    public ArrayList<T> getFruits() {
        return fruits;
    }

    public double getWeight() {
        if (!fruits.isEmpty()) {
            return fruits.size() * fruits.get(0).getWeight();
        }
        return 0;
    }

    public boolean compare(Box<?> boxTwo) {
        return Math.abs(this.getWeight() - boxTwo.getWeight()) < 0.0001;
    }

    public void pourIntoBox(Box<T> boxTwo) {
        boxTwo.addFruits(getFruits());
        fruits.clear();
    }
}
