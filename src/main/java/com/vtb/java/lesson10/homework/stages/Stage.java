package com.vtb.java.lesson10.homework.stages;

import com.vtb.java.lesson10.homework.Car;

public abstract class Stage {
    int length;
    String description;

    public String getDescription() {
        return description;
    }

    public abstract void overcome(Car c);
}

