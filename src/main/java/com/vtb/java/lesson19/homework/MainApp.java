package com.vtb.java.lesson19.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainApp {
    public static void main(String[] args) {
        HibernateSessionFactory hsf = new HibernateSessionFactory();
        DataBaseAPI dataBase = new DataBaseAPI(hsf);
        CountDownLatch cdl = new CountDownLatch(8);
        PrepareDataApp.prepareData(dataBase);
        List<User> users = new ArrayList<>();

        dataBase.beginTransaction();
        users = dataBase.getUsers();
        dataBase.commit();
        dataBase.close();

        long time = System.currentTimeMillis();
        for (User user : users) {
            new UserThreadOptimistic(user.getName(), user, cdl, hsf).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Затраченное время при использовании оптимистической блокировки: %s мс\n", System.currentTimeMillis() - time);

//        long time = System.currentTimeMillis();
//        for (User user : users) {
//            new UserThreadPessimistic(user.getName(), user, cdl, hsf).start();
//        }
//        try {
//            cdl.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.printf("Затраченное время при использовании пессимистической блокировки: %s мс\n", System.currentTimeMillis() - time);

        dataBase.beginTransaction();
        long betSum = dataBase.getBetSum();
        if (checkBetSum(betSum, users.size(), 1000, 100)) {
            System.out.println("Проверка успешно пройдена!");
        }
        dataBase.commit();
        dataBase.close();

        hsf.shutdown();

    }

    public static boolean checkBetSum(long betSum, long usersCol, long betCol, long bet) {
        long sum = usersCol * betCol * bet;
        System.out.printf("Сумма ставок в БД = %s\tСумма ставок должна быть равна = %s\n", betSum, sum);
        return sum == betSum;
    }

//    Затраченное время при использовании оптимистической блокировки: 14805 мс
//    Затраченное время при использовании пессимистической блокировки: 3040 мс
}
