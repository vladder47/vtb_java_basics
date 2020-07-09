package com.vtb.java.lesson10.homework;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static CyclicBarrier cb = new CyclicBarrier(Race.COMPETITORS_COUNT);
    private static ReentrantLock lock = new ReentrantLock();

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    private void setWinner() {
        if (lock.tryLock()) {
            lock.lock();
            System.out.println(this.name + " - WIN");
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            Race.cdl.countDown();
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).overcome(this);
        }
        Race.cdl.countDown();
        setWinner();
    }
}

