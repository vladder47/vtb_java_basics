package com.vtb.java.lesson3.homework;

public class Robot implements Participant {
    private String name;
    private int maxDistance;
    private int maxHeight;
    private boolean checkPass;

    public Robot(String name, int maxDistance, int maxHeight) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxHeight = maxHeight;
        this.checkPass = true;
    }

    @Override
    public String toString() {
        return String.format("Робот Имя: %s\tМакс.дистанция: %dм\tМакс.высота прыжка: %dм", name, maxDistance, maxHeight);
    }

    @Override
    public void run(int distance) {
        if (distance <= maxDistance) {
            System.out.printf("Робот %s пробежал %dм\n", name, distance);
        } else {
            System.out.printf("Робот %s не может пробежать более %dм!\n", name, maxDistance);
            checkPass = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxHeight) {
            System.out.printf("Робот %s подпрыгнул на %dм\n", name, height);
        } else {
            System.out.printf("Робот %s не может подпрыгнуть более чем на %dм!\n", name, maxHeight);
            checkPass = false;
        }
    }

    @Override
    public boolean passBarrier(Barrier barrier) {
        if (barrier instanceof Track) {
            run(((Track) barrier).getLength());
        } else if (barrier instanceof Wall) {
            jump(((Wall) barrier).getHeight());
        }
        if (checkPass) {
            System.out.printf("Робот %s прошел препятствие %s\n", name, barrier.getName());
        } else {
            System.out.printf("Робот %s не прошел препятствие %s\n", name, barrier.getName());
        }
        return checkPass;
    }
}
