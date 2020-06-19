package com.vtb.java.lesson2.homework;

public class Employee {
    private String name;
    private String email;
    private int age;
    private String position;

    public Employee(String name, String email, int age, String position) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.position = position;
    }

    // вывод информации о сотруднике
    public void getInfo() {
        System.out.println("Имя: " + name + "\tE-mail: " + email + "\tВозраст: " + age + "\tДолжность: " + position);
    }
}
