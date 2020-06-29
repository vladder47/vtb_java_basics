package com.vtb.java.lesson5.homework.task3;

import java.util.ArrayList;
import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) {
        Apple appOne = new Apple();
        Apple appTwo = new Apple();
        Apple appThree = new Apple();
        Apple appFour = new Apple();

        Orange orangeOne = new Orange();
        Orange orangeTwo = new Orange();
        Orange orangeThree = new Orange();

        ArrayList<Apple> apples = new ArrayList<>(Arrays.asList(appOne, appTwo, appThree, appFour));

        Box<Apple> boxOne = new Box<>();
        boxOne.addFruit(appOne);
        boxOne.addFruit(appTwo);
        boxOne.addFruit(appThree);

        Box<Orange> boxTwo = new Box<>();
        boxTwo.addFruit(orangeOne);
        boxTwo.addFruit(orangeTwo);

        Box<Apple> boxThree = new Box<>();
        boxThree.addFruits(apples);

        System.out.println("Вес первой коробки: " + boxOne.getWeight());
        System.out.println("Вес второй коробки: " + boxTwo.getWeight());
        System.out.println("Вес третьей коробки: " + boxThree.getWeight());
        System.out.println("Сравнение веса первой и второй коробки: " + boxOne.compare(boxTwo));
        System.out.println("Сравнение веса первой и третьей коробки: " + boxOne.compare(boxThree));

        System.out.println("Пересыпаем яблоки из первой коробки во вторую");
        boxOne.pourIntoBox(boxThree);
        System.out.println("Вес первой коробки: " + boxOne.getWeight());
        System.out.println("Вес третьей коробки: " + boxThree.getWeight());

        boxThree.pourIntoBox(boxThree);
        System.out.println("Вес третьей коробки: " + boxThree.getWeight());
    }
}
