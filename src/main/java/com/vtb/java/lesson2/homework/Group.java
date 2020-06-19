package com.vtb.java.lesson2.homework;

import java.util.Arrays;

public class Group {
    private String name;
    private Employee[] employees;
    private int count;

    public Group(String name) {
        this.name = name;
        this.employees = new Employee[10];
        this.count = 0;
    }

    // конструктор с возможностью передачи в него массива с сотрудниками
    // если массив слишком большой, то берется его копия длиной 10 элементов
    public Group(String name, Employee[] employees) {
        this.name = name;
        if (employees.length <= 10) {
            this.employees = employees;
            this.count = employees.length;
        } else {
            this.employees = Arrays.copyOfRange(employees, 0, 10);
            this.count = 10;
        }
    }

    // добавление сотрудника
    public void addEmployee(Employee emp) {
        if (count < 10) {
            this.employees[count] = emp;
            count += 1;
        } else {
            System.out.println("В группе максимальное число сотрудников!");
        }
    }

    // удаление сотрудника по индексу
    public void deleteEmployee(int index) {
        if (employees[index] != null && index < 10) {
            employees[index] = null;
            for (int i = index + 1; i < count; i++) {
                employees[i - 1] = employees[i];
                employees[i] = null;
            }
            count -= 1;
            System.out.println("Удален сотрудник под номером " + index);
        } else {
            System.out.println("Такого сотрудника нет!");
        }
    }

    // удаление всех сотрудников
    public void deleteAllEmployees() {
        System.out.println("Все сотрудники удалены!");
        this.employees = new Employee[10];
        count = 0;
    }

    // вывод информации обо всех сотрудниках группы
    public void getAllInfo() {
        if (count == 0) {
            System.out.println("В группе никого нет!");
        }
        for (Employee e: employees) {
            if (e != null) {
                e.getInfo();
            } else {
                break;
            }
        }
    }
}
