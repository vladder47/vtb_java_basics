package com.vtb.java.lesson3.homework;

public class MainApp {
    public static void main(String[] args) {
        Participant[] participants = {
                new Human("Владислав", 200, 2),
                new Human("Денис", 300, 3),
                new Cat("Мурзик", 500, 5),
                new Cat("Леопольд", 550, 6),
                new Robot("Терминатор", 1000, 20),
                new Robot("Робокоп", 2000, 30)
        };

        Barrier[] barriers = {
                new Track("Беговая дорожка №1", 200),
                new Wall("Стена №1", 2),
                new Track("Беговая дорожка №2", 300),
                new Wall("Стена №2", 3),
                new Track("Беговая дорожка №3", 1000),
                new Wall("Стена №3", 5),
        };

        System.out.println("Список участников: ");
        for (Participant p: participants) {
            System.out.println(p.toString());
        }

        System.out.println("Список препятствий: ");
        for (Barrier b: barriers) {
            System.out.println(b.toString());
        }

        for (Participant p : participants) {
            System.out.println("Прохождение препятствий участником:");
            for (Barrier b : barriers) {
                if (!p.passBarrier(b)) {
                    break;
                }
            }
            System.out.println();
        }
    }
}
