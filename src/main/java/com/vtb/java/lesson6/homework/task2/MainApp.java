package com.vtb.java.lesson6.homework.task2;

import java.util.LinkedHashSet;
import java.util.Set;

public class MainApp {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.add("Иванов", "8-909-132-11-11");
        pb.add("Петров", "8-909-132-22-22");
        pb.add("Сидоров", "8-909-132-33-33");
        pb.add("Горбунов", "8-909-132-44-44");
        pb.add("Иванов", "8-909-132-55-55");
        pb.add("Петров", "8-909-132-66-66");
        pb.add("Сидоров", "8-909-132-77-77");

        String[] persons = {"Иванов", "Петров", "Сидоров", "Горбунов", "Макаров"};
        for (String person : persons) {
            Set<String> phones = pb.get(person);
            if (phones != null) {
                System.out.printf("Фамилия: %s\tТелефон(-ы): %s\n", person, phones);
            } else {
                System.out.printf("Фамилия: %s\tТелефон(-ы): Телефонов нет\n", person);
            }
        }
    }

}
