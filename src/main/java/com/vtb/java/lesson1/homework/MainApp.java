package com.vtb.java.lesson1.homework;

import java.util.Arrays;

public class MainApp {

    public static void main(String[] args) {
        // 1-е задание
        System.out.println(checkSum(5, 10));
        System.out.println(checkSum(2, 3));
        System.out.println(checkSum(5, 2));

        // 2-е задание
        checkPositiveOrNegative(0);
        checkPositiveOrNegative(-23);

        // 3-е задание
        System.out.println(isNegative(-2));
        System.out.println(isNegative(2));

        // 4-е задание
        sayHelloTo("Владислав");

        // 5-е задание
        int[] arr = {0, 0, 0, 1, 1, 0, 1, 0, 1};
        System.out.println("Массив до изменения:" + Arrays.toString(arr));
        replaceZerosAndOnes(arr);
        System.out.println("Массив после изменения:" + Arrays.toString(arr));

        // 6-е задание
        int[] arr2 = new int[8];
        int temp = 2;
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = temp + (3 * i);
        }
        System.out.println("Массив для 6-го задания:" + Arrays.toString(arr2));

        // 7-е задание
        int[] arr3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Исходный массив для 7-го задания:" + Arrays.toString(arr3));
        for (int i = 0; i < arr3.length; i++) {
            if (arr3[i] < 6) {
                arr3[i] *= 2;
            }
        }
        System.out.println("Итоговый массив для 7-го задания:" + Arrays.toString(arr3));

        // 8-е задание
        int n = 4;
        int[][] matrix = new int[n][n];
        System.out.println("Исходная матрица для 8-го задания:");
        printMatrix(matrix);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == j) || (i + j == n - 1)) {
                    matrix[i][j] = 1;
                }
            }
        }
        System.out.println("Итоговая матрица для 8-го задания:");
        printMatrix(matrix);

        // 9-е задание
        int[] arr4 = {2, 4, 6, 0, 19, 123, 0, -2, 34, 56};
        System.out.println("Исходный массив для 9-го задания:" + Arrays.toString(arr4));
        findMinAndMax(arr4);

        // 10-е задание
        isYearLeap(2012);
        isYearLeap(1900);
        isYearLeap(1903);
        isYearLeap(2000);

        // 11-е задание
        int[] arr5 = {2, 2, 2, 1, 2, 2, 10, 1};
        int[] arr6 = {1, 1, 1, 2, 1};
        int[] arr7 = {2, 3, 4, 5, 6, 1};
        System.out.println("Массив arr5:" + Arrays.toString(arr5));
        System.out.println("Массив arr6:" + Arrays.toString(arr6));
        System.out.println("Массив arr7:" + Arrays.toString(arr7));
        System.out.println("checkBalance для массива arr5: " + checkBalance(arr5));
        System.out.println("checkBalance для массива arr6: " + checkBalance(arr6));
        System.out.println("checkBalance для массива arr7: " + checkBalance(arr7));
    }

    // метод для 1-го задания
    public static boolean checkSum(int a, int b) {
        return (a + b) >= 10 && (a + b) <= 20;
    }

    // метод для 2-го задания
    public static void checkPositiveOrNegative(int a) {
        if (a >= 0) {
            System.out.println("Число положительное");
        } else {
            System.out.println("Число отрицательное");
        }
    }

    // метод для 3-го задания
    public static boolean isNegative(int a) {
        return a < 0;
    }

    // метод для 4-го задания
    public static void sayHelloTo(String s) {
        System.out.println("Привет, " + s + "!");
    }

    // метод для 5-го задания
    public static void replaceZerosAndOnes(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = 1;
            } else if (arr[i] == 1){
                arr[i] = 0;
            }
        }
    }

    // метод для построчного вывода матрицы
    public static void printMatrix(int[][] matr) {
        for (int[] m: matr) {
            System.out.println(Arrays.toString(m));
        }
    }

    // метод для 9-го задания
    public static void findMinAndMax(int[] arr) {
        int min = arr[0];
        int max = arr[0];
        for (int value : arr) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        System.out.println("Минимальное число в массиве: " + min);
        System.out.println("Максимальное число в массиве: " + max);
    }

    // метод для 10-го задания
    public static void isYearLeap(int year) {
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            System.out.println("Год " + year + " - високосный");
        } else {
            System.out.println("Год " + year + " - не високосный");
        }
    }

    // метод для 11-го задания
    public static boolean checkBalance(int[] arr) {
        int left = 0;
        int right = 0;
        // i - граница между левой и правой частью
        for (int i = 1; i < (arr.length - 1); i++) {
            // проходим по массиву
            for (int j = 0; j < arr.length; j++) {
                if (j < i) {
                    left += arr[j];
                } else {
                    right += arr[j];
                }
            }
            if (left == right) {
                return true;
            }
            left = 0;
            right = 0;
        }
        return false;
    }
}