package com.vtb.java.lesson3.homework;

public class Track extends Barrier {
    private int length;

    public Track(String name, int length) {
        super(name);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return String.format("Имя: %s\tДистанция: %dм", getName(), length);
    }
}
