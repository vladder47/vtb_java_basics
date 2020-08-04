package com.vtb.java.lesson18.homework;

import org.hibernate.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

// предварительное создание таблиц для сервиса
public class PrepareDataApp {
    public static void prepareData(HibernateSessionFactory hsf) {
        try (Session session = hsf.getSession()) {
            String sql = Files.lines(Paths.get("create_tables.sql")).collect(Collectors.joining(" "));
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
