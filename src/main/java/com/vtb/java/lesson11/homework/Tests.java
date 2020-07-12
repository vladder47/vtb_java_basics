package com.vtb.java.lesson11.homework;

public class Tests {
    @BeforeSuite
    public static void initialMethod() {
        System.out.println("Метод, выполняющийся перед запуском основных тестов");
    }

//    @BeforeSuite
//    public static void initialMethodTwo() {
//        System.out.println("Метод, выполняющийся перед запуском основных тестов");
//    }

    @Test(priority = 5)
    public static void testOne() {
        System.out.println("Первый тест (приоритет - 5)");
    }

    @Test(priority = 10)
    public static void testTwo() {
        System.out.println("Второй тест (приоритет - 10)");
    }

    @Test(priority = 2)
    public static void testThree() {
        System.out.println("Третий тест (приоритет - 2)");
    }

    @Test
    public static void testFour() {
        System.out.println("Четвёртый тест (приоритет - 1)");
    }

    @Test(priority = 10)
    public static void testFive() {
        System.out.println("Пятый тест (приоритет - 10)");
    }

    @AfterSuite
    public static void finalMethod() {
        System.out.println("Метод, выполняющийся по завершении основных тестов");
    }

//    @AfterSuite
//    public static void finalMethodTwo() {
//        System.out.println("Метод, выполняющийся по завершении основных тестов");
//    }

}
