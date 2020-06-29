package com.vtb.java.lesson6.homework;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        String[] words = {"cat", "cat", "cat", "dog", "cat", "dog", "cat",
                "cat", "hello", "hello", "cat", "world", "world"};
        System.out.println("Исходный массив: " + Arrays.toString(words));
        Set<String> uniqueWords = getUniqueWords(words);
        System.out.println("Уникальные слова: " + uniqueWords);

        Map<String, Integer> countWords = getCountWords(words);
        for (Map.Entry<String, Integer> cw : countWords.entrySet()) {
            System.out.printf("Слово: %s\tКоличество: %s\n", cw.getKey(), cw.getValue());
        }
    }

    public static HashSet<String> getUniqueWords(String[] arr) {
        return new HashSet<>(Arrays.asList(arr));
    }

    public static HashMap<String, Integer> getCountWords(String[] arr) {
        HashMap<String, Integer> result = new HashMap<>();
        for (String s : arr) {
            if (result.containsKey(s.toLowerCase())) {
                result.put(s.toLowerCase(), result.get(s) + 1);
            } else {
                result.put(s.toLowerCase(), 1);
            }
        }
        return result;
    }
}
