package com.vtb.java.lesson5.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        Integer[] arrOne = new Integer[]{1, 2, 3, 4, 5};
        String[] arrTwo = new String[]{"a", "b", "c", "d", "e"};

        System.out.println("Первый массив до: " + Arrays.toString(arrOne));
        System.out.println("Второй массив до: " + Arrays.toString(arrTwo));
        swapArrayElements(arrOne, 2, 4);
        swapArrayElements(arrTwo, 2, 4);
        System.out.println("Первый массив после: " + Arrays.toString(arrOne));
        System.out.println("Второй массив после: " + Arrays.toString(arrTwo));

        try{
            swapArrayElements(arrOne, 2, 6);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Вы вышли за пределы массива!");
        }

        List<Integer> arrListOne = arrayToArrayList(arrOne);
        System.out.println("Преобразование первого массива к типу: " + arrListOne.getClass());
        System.out.println("Вывод получившегося списка: " + arrListOne.toString());
    }

    // метод, котроый меняет два элемента массива местами
    public static void swapArrayElements(Object[] arr, int firstIndex, int secondIndex) {
        Object temp = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = temp;
    }

    // метод, который преобразует массив в ArrayList
    public static <T> List<T> arrayToArrayList(T[] arr) {
        List<T> result = new ArrayList<>();
        for (T elem : arr) {
            result.add(elem);
        }
        return result;
    }

}
