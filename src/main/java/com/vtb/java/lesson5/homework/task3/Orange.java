package com.vtb.java.lesson5.homework.task3;

public class Orange extends Fruit {
    public Orange() {
        super("Апельсин", 1.5f);
    }

    @Override
    public String toString() {
        return "Апельсин\tНазвание: " + this.name + "\tВес: " + this.weight;
    }
}
