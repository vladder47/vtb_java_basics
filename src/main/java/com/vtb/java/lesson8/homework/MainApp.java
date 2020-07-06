package com.vtb.java.lesson8.homework;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class MainApp {
    public static void main(String[] args) {
        String str = "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty " +
                "hello hello world world the the the the qwerty qwerty";
        System.out.println("Преобразованная строка для первого задания: " + getWordsLongerThanFiveChar(str));

        String[][] wordsMatrix = {{"hello", "world", "real", "human", "being"},
                {"hello", "world", "life", "human", "being"},
                {"hello", "world", "real", "human", "matrix"},
                {"set", "world", "real", "dog", "being"},
                {"hello", "world", "real", "cat", "being"}};
        System.out.println("Список уникальных слов: " + getUniqueWords(wordsMatrix));

        System.out.println("Сумма четных чисел от 100 до 200: " + getSumEvenNum(100, 200));

        String[] arr = {"hello", "world", "i", "am", "human", "aa"};
        System.out.println("Суммарная длина строк: " + getTotalLinesLength(arr));

        System.out.println("Первые три слова в алфавитном порядке: " + getThreeWordsInAlphOrder(arr));
    }

    // метод для первого задания
    public static String getWordsLongerThanFiveChar(String str) {
        return Arrays.stream(str.split("\\s"))
                .filter(s -> s.length() > 5)
                .collect(Collectors.joining(" "));
    }

    // метод для второго задания
    public static List<String> getUniqueWords(String[][] str) {
        return Arrays.stream(str)
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    // метод для третьего задания
    public static int getSumEvenNum(int start, int stop) {
        return IntStream.rangeClosed(start, stop)
                .filter(n -> n % 2 == 0)
                .sum();
    }

    // метод для четвертого задания
    public static int getTotalLinesLength(String[] arr) {
        return Arrays.stream(arr)
                .mapToInt(String::length)
                .reduce(0, Integer::sum);
    }

    // метод для пятого задания
    public static List<String> getThreeWordsInAlphOrder(String[] arr) {
        return Arrays.stream(arr)
                .sorted()
                .limit(3)
                .collect(Collectors.toList());
    }
}
