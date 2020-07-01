package com.vtb.java.lesson6.homework;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        String[] words = {"cat", "cat", "Cat", "dog", "cat", "dog", "cat",
                "cat", "hello", "hello", "cat", "world", "world"};
        System.out.println("Исходный массив: " + Arrays.toString(words));
        Set<String> uniqueWords = getUniqueWordsIgnoreCase(words);
        System.out.println("Уникальные слова: " + uniqueWords);

        Map<String, Integer> countWords = getCountWords(words);
        for (Map.Entry<String, Integer> cw : countWords.entrySet()) {
            System.out.printf("Слово: %s\tКоличество: %s\n", cw.getKey(), cw.getValue());
        }
    }

    // с учетом регистра
    public static HashSet<String> getUniqueWords(String[] arr) {
        return new HashSet<>(Arrays.asList(arr));
    }

    // без учета регистра
    public static Set<String> getUniqueWordsIgnoreCase(String[] arr) {
        Set<String> result = new HashSet<>();
        for (String s : arr) {
            result.add(s.toLowerCase());
        }
        return result;
    }

    public static HashMap<String, Integer> getCountWords(String[] arr) {
        HashMap<String, Integer> result = new HashMap<>();
        for (String s : arr) {
            s = s.toLowerCase();
            if (result.containsKey(s)) {
                result.put(s, result.get(s) + 1);
            } else {
                result.put(s, 1);
            }
        }
        return result;
    }
}
