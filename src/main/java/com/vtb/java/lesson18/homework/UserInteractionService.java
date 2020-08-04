package com.vtb.java.lesson18.homework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class UserInteractionService {
    private final DataBaseAPI dataBaseAPI;

    public UserInteractionService(DataBaseAPI dataBaseAPI) {
        this.dataBaseAPI = dataBaseAPI;
    }

    public void startService() {
        Scanner sc = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Введите запрос (exit - для выхода): ");
                String query = sc.nextLine();
                if (query.equals("exit")) {
                    break;
                }
                runCommand(query);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void runCommand(String query) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] splitCommand = query.split(" ");
        String command = splitCommand[0].substring(1);
        // если в строке два аргумента (помимо самой команды), то вызываем метод buy
        // в остальных же методах аргумент в любом случае один
        if (splitCommand.length > 2) {
            Method method = UserInteractionService.class.getDeclaredMethod(command, Long.class, Long.class);
            method.invoke(this, Long.parseLong(splitCommand[1]), Long.parseLong(splitCommand[2]));
        } else {
            Method method = UserInteractionService.class.getDeclaredMethod(command, String.class);
            method.invoke(this, splitCommand[1]);
        }
    }

    private void showProductsByConsumer(String name) {
        List<Product> products = dataBaseAPI.getProductsByConsumer(name);
        List<Long> purchaseCosts = dataBaseAPI.getPurchasesCost(name);
        for (int i = 0; i < products.size(); i++) {
            products.get(i).info();
            System.out.printf("\tЦена на момент покупки: %d\n", purchaseCosts.get(i));
        }
    }

    private void showConsumersByProductTitle(String name) {
        List<Client> clients = dataBaseAPI.getConsumersByProductTitle(name);
        for (Client client : clients) {
            client.info();
        }
    }

    private void deleteConsumer(String name) {
        dataBaseAPI.deleteClient(name);
        System.out.printf("Клиент с именем %s успешно удален\n", name);
    }

    private void deleteProduct(String name) {
        dataBaseAPI.deleteProduct(name);
        System.out.printf("Продукт с названием %s успешно удален\n", name);
    }

    private void buy(Long clientId, Long productId) {
        dataBaseAPI.buyProduct(clientId, productId);
        System.out.println("Товар успешно приобретен!");
    }
}
