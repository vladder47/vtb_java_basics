package com.vtb.java.lesson7.homework;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        int[] elements = {10, 100, 10000, 100000};
        for (int elem : elements) {
            List<Integer> lstOne = generateArrayList(elem);
            List<Integer> lstTwo = new LinkedList<>(lstOne);
            System.out.printf("Количество элементов в массиве: %d\n", elem);
//            accessToMiddleElement(lstOne);
//            accessToMiddleElement(lstTwo);
            removeMiddleElements(lstOne);
            removeMiddleElements(lstTwo);
        }
    }

    // генерация ArrayList
    public static List<Integer> generateArrayList(int size) {
        List<Integer> lst = new ArrayList<>(size);
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            lst.add(r.nextInt(100));
        }
        return lst;
    }

    // метод, используемый для обращения по индексу к среднему элементу List
    public static void accessToMiddleElement(List<Integer> lst) {
        int size = lst.size();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            int temp = lst.get(size / 2);
        }
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    // Результаты обращения по индексу к среднему элементу ArrayList и LinkedList
    // * по 10000 обращений
    //              10  100  10000  100000
    // ArrayList:    1    1      1       0
    // LinkedList:   1    1     69     923

    // метод, удаляющий центральные элементы по индексу
    public static void removeMiddleElements(List<Integer> lst) {
        int size = lst.size();
        long time = System.currentTimeMillis();
        for (int i = 0; i < size / 2; i++) {
            lst.remove(lst.size() / 2);
        }
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    // Результаты удаления центральных элементов из ArrayList и LinkedList
    // * удаляется ровно половина элементов
    //              10  100  10000  100000
    // ArrayList:    0    0      2     153
    // LinkedList:   0    0     30    2781
}
