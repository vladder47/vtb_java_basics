package com.vtb.java.lesson19.homework;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class UserThreadPessimistic extends Thread {
    private final User user;
    private final CountDownLatch cdl;
    private final HibernateSessionFactory hsf;

    public UserThreadPessimistic(String name, User user, CountDownLatch cdl, HibernateSessionFactory hsf) {
        super(name);
        this.user = user;
        this.cdl = cdl;
        this.hsf = hsf;
    }

    @Override
    public void run() {
        System.out.printf("Поток %s стартовал...\n", Thread.currentThread().getName());
        placeBet(100);
        cdl.countDown();
        System.out.printf("Поток %s завершён...\n", Thread.currentThread().getName());
    }

    public void placeBet(long bet) {
        Random r = new Random();
        DataBaseAPI dataBase = new DataBaseAPI(hsf);
        try {
            for (int i = 0; i < 1000; i++) {
                dataBase.beginTransaction();
                Long lotId = (long) r.nextInt(4) + 1;
                Long userId = user.getId();
                dataBase.raiseLotBetWithLock(userId, lotId, bet);
                dataBase.commit();
                sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dataBase.close();
        }
    }
}
