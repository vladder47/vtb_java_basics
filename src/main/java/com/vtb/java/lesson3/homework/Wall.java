package com.vtb.java.lesson3.homework;

public class Wall extends Barrier {
    private int height;

    public Wall(String name, int height) {
        super(name);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("Имя: %s\tВысота: %dм", getName(), height);
    }
}
