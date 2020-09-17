package com.vtb.java.lesson19.homework;

import org.hibernate.Session;

import javax.persistence.OptimisticLockException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class UserThreadOptimistic extends Thread {
    private final User user;
    private final CountDownLatch cdl;
    private final HibernateSessionFactory hsf;

    public UserThreadOptimistic(String name, User user, CountDownLatch cdl, HibernateSessionFactory hsf) {
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
        dataBase.beginTransaction();
        try {
            for (int i = 0; i < 1000; i++) {
                Long lotId = (long) r.nextInt(4) + 1;
                Long userId = user.getId();
                dataBase.raiseLotBet(userId, lotId, bet);
                sleep(1);
            }
            dataBase.commit();
        } catch (OptimisticLockException e) {
            dataBase.rollBack();
            System.out.printf("Rollback потока %s...\n", Thread.currentThread().getName());
            placeBet(bet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dataBase.close();
        }
    }
}
