package com.vtb.java.lesson7.homework.task3;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        List<MyEntry> lst = generateMyEntryArrayList(50000);
        Map<Integer, Integer> hm = fillHashMap(lst);
        accessToValueByKey(hm);
        accessToMyEntryValue(lst);
    }

    // генерация ArrayList
    public static List<MyEntry> generateMyEntryArrayList(int size) {
        List<MyEntry> lst = new ArrayList<>(size);
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            lst.add(new MyEntry(i, r.nextInt(100)));
        }
        return lst;
    }

    // заполнение HashMap значениями из ArrayList<MyEntry>
    public static Map<Integer, Integer> fillHashMap(List<MyEntry> lst) {
        Map<Integer, Integer> hm = new HashMap<>();
        for (MyEntry item : lst) {
            hm.put(item.getKey(), item.getValue());
        }
        return hm;
    }

    // метод для поиска значения по ключу в HashMap
    public static void accessToValueByKey(Map<Integer, Integer> map) {
        Random r = new Random();
        int size = map.size();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            int key = r.nextInt(size);
            int value = map.get(key);
        }
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    // метод для поиска значения по ключу в ArrayList<MyEntry>
    public static void accessToMyEntryValue(List<MyEntry> lst) {
        Random r = new Random();
        int size = lst.size();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            int key = r.nextInt(size);
            for (MyEntry item : lst) {
                if (item.getKey() == key) {
                    int value = item.getValue();
                }
            }
        }
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    // Результаты поиска значения по ключу в HashMap и ArrayList<MyEntry>
    // * по 50000 записей в каждом
    // HashMap: 17
    // ArrayList<MyEntry>: 13705

    // HashMap работает в 806 раз быстрее
}
