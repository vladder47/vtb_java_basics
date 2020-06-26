package com.vtb.java.lesson4.homework;

public class MainApp {
    public static void main(String[] args) {
        String[][] arrOne = {
                new String[]{"12", "13", "14", "15"},
                new String[]{"12", "13", "14", "15"},
                new String[]{"12", "13", "14", "15"},
                new String[]{"12", "13", "14", "15"}
        };
        String[][] arrTwo = {
                new String[]{"12", "13", "14", "15", "12"},
                new String[]{"12", "13", "14", "15", "12"},
                new String[]{"12", "13", "14", "15", "12"},
                new String[]{"12", "13", "14", "15", "12"},
                new String[]{"12", "13", "14", "15", "12"}
        };
        String[][] arrThree = {
                new String[]{"12", "13", "14", "15"},
                new String[]{"12", "abs", "14", "15", "23"},
                new String[]{"12", "13", "14", "15"},
                new String[]{"12", "13", "14", "15"}
        };

        try {
            System.out.printf("Сумма элементов массива равна %d", sumArrayElem(arrOne));
            //System.out.printf("Сумма элементов массива равна %d", sumArrayElem(arrTwo));
            //System.out.printf("Сумма элементов массива равна %d", sumArrayElem(arrThree));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static int sumArrayElem(String[][] arr) {
        if (arr.length != 4) {
            throw new MyArraySizeException("Массив должен быть размером 4х4");
        } else {
            for (String[] strings : arr) {
                if (strings.length != 4) {
                    throw new MyArraySizeException("Массив должен быть размером 4х4");
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Не удалось выполнить преобразование строки в число в ячейке [%d][%d]", i, j));
                }
            }
        }
        return sum;
    }
}
