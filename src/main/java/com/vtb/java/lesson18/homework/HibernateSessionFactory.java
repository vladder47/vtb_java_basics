package com.vtb.java.lesson18.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private final SessionFactory sessionFactory;

    public HibernateSessionFactory() {
        this.sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        return new Configuration()
                .configure("configs/lesson18/hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
