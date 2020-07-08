package com.vtb.java.lesson9.homework;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class MainApp {
    public static void main(String[] args) {
        int[] arr = generateIntArray(100_000_000);

        long time = System.currentTimeMillis();
        int res = searchMaxElem(arr);
        System.out.println("Время при вызове однопоточного варианта: " + (System.currentTimeMillis() - time) + "мс");
        System.out.println("Максимальный элемент в массиве: " + res);

        long time1 = System.currentTimeMillis();
        int res1 = searchMaxElemRecursiveTask(arr);
        System.out.println("Время при вызове варианта c использованием RecursiveTask: " + (System.currentTimeMillis() - time1) + "мс");
        System.out.println("Максимальный элемент в массиве: " + res1);

        long time2 = System.currentTimeMillis();
        int res2 = searchMaxElemStream(arr);
        System.out.println("Время при вызове варианта с использованием Stream: " + (System.currentTimeMillis() - time2) + "мс");
        System.out.println("Максимальный элемент в массиве: " + res2);

        long time3 = System.currentTimeMillis();
        int res3 = searchMaxElemParallelStream(arr);
        System.out.println("Время при вызове варианта с использованием ParallelStream: " + (System.currentTimeMillis() - time3) + "мс");
        System.out.println("Максимальный элемент в массиве: " + res3);
    }

    // генерация массива
    public static int[] generateIntArray(int size) {
        int[] arr = new int[size];
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(100_000);
        }
        return arr;
    }

    // однопоточный вариант
    public static int searchMaxElem(int[] data) {
        int result = -1;
        for (int value : data) {
            if (value > result) {
                result = value;
            }
        }
        return result;
    }

    // вариант с использованием RecursiveTask
    public static int searchMaxElemRecursiveTask(int[] data) {
        MaxElemSearch mes = new MaxElemSearch(data, 0, 100_000_000);
        //mes.setData(data);
        return ForkJoinPool.commonPool().invoke(mes);
    }

    // вариант с использованием Stream
    public static int searchMaxElemStream(int[] data) {
        return Arrays.stream(data)
                .reduce(0, Integer::max);
    }

    // вариант с использованием ParallelStream
    public static int searchMaxElemParallelStream(int[] data) {
        return Arrays.stream(data)
                .parallel()
                .reduce(0, Integer::max);
    }

    // Результаты поиска максимального элемента в целочисленном массиве
    // * размер массива - 100_000_000
    // Однопоточный вариант: 44 мс
    // Вариант с использованием RecursiveTask: 54 мс
    // Вариант с использованием Stream: 63 мс
    // Вариант с использованием ParallelStream: 50 мс
    // ВЫВОД: быстрее всего работает однопоточный вариант
    // Исходя из этого, можно сделать вывод, что добавление многопоточности для задачи
    // нахождения максимума не ускоряет работу программы
}
