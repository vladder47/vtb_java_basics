package com.vtb.java.lesson2.homework;

public class MainApp {

    public static void main(String[] args) {
        Employee emp1 = new Employee("Владислав", "vladdrozdov@gmail.com", 22,"Стажер");
        Employee emp2 = new Employee("Борис", "borisemp@gmail.com", 25,"Frontend-разработчик");
        Employee emp3 = new Employee("Вячеслав", "vyaches123@gmail.com", 27,"Системный аналитик");
        Employee emp4 = new Employee("Денис", "denisdemq@gmail.com", 21,"Тестировщик");
        Employee emp5 = new Employee("Максим", "ivanovmaksim@gmail.com", 34,"Ведущий разработчик");

        Group group1 = new Group("Первая команда");
        group1.addEmployee(emp1);
        group1.addEmployee(emp2);
        group1.addEmployee(emp3);
        group1.addEmployee(emp4);
        group1.addEmployee(emp5);
        System.out.println("Первая команда: ");
        group1.getAllInfo();

        group1.deleteEmployee(3);
        group1.deleteEmployee(6);
        System.out.println("Первая команда: ");
        group1.getAllInfo();

        group1.deleteAllEmployees();
        System.out.println("Первая команда: ");
        group1.getAllInfo();

        Employee[] employees = {emp1, emp2, emp3, emp4, emp5};
        Group group2 = new Group("Вторая команда", employees);
        System.out.println("Вторая команда: ");
        group2.getAllInfo();
    }
}
