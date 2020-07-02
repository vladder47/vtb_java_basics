package com.vtb.java.lesson6.homework.task2;

import java.util.*;

public class PhoneBook {
    // использовал LinkedHashSet, чтобы номера телефонов не дублировались
    // и выводились в том порядке, в котором были занесены
    private HashMap<String, LinkedHashSet<String>> phoneBook;

    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    public void add(String lastName, String phoneNumber) {
        if (phoneBook.containsKey(lastName)) {
            phoneBook.get(lastName).add(phoneNumber);
        } else {
            phoneBook.put(lastName, new LinkedHashSet<>(Collections.singletonList(phoneNumber)));
        }
    }

    public LinkedHashSet<String> get(String lastName) {
        return phoneBook.get(lastName);
    }
}
