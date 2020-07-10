package com.vtb.java.lesson10.homework;

import com.vtb.java.lesson10.homework.stages.*;

public class RaceApp {
    public static void main(String[] args) {
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        race.begin();
    }
}

