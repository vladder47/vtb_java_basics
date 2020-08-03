package com.vtb.java.lesson18.homework;

import org.hibernate.Session;

public class MainApp {
    public static void main(String[] args) {
        HibernateSessionFactory hsf = new HibernateSessionFactory();
        DataBaseAPI dataBase = new DataBaseAPI(hsf);
        PrepareDataApp.prepareData(dataBase);
        UserInteractionService uis = new UserInteractionService(dataBase);
        uis.startService();
        hsf.shutdown();
    }
}
