package com.vtb.java.lesson19.homework;

import org.hibernate.Session;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class DataBaseAPI {
    private final HibernateSessionFactory hsf;
    private Session session;

    public DataBaseAPI(HibernateSessionFactory hsf) {
        this.hsf = hsf;
        this.session = null;
    }

    public void beginTransaction() {
        session = hsf.getSession();
        session.beginTransaction();
    }

    public void createNativeQuery(String query) {
        session.createNativeQuery(query).executeUpdate();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollBack() {
        session.getTransaction().rollback();
    }

    public void close() {
        if (session != null) {
            session.close();
        }
    }

    public Lot getLot(Long id) {
        return session.get(Lot.class, id);
    }

    public Lot getLotWithLock(Long id) {
        Query query = session.createQuery("SELECT l FROM Lot l WHERE l.id = :id", Lot.class);
        query.setParameter("id", id);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return (Lot) query.getSingleResult();
    }

    public List<Lot> getLots() {
        TypedQuery<Lot> query = session.createQuery("SELECT l FROM Lot l", Lot.class);
        return query.getResultList();
    }

    public User getUser(Long id) {
        return session.get(User.class, id);
    }

    public List<User> getUsers() {
        TypedQuery<User> query = session.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public void raiseLotBet(Long userId, Long lotId, Long bet) {
        Lot lot = getLot(lotId);
        User user = getUser(userId);
        lot.setCurrentBet(lot.getCurrentBet() + bet);
        lot.setLastBetOwner(user);
    }

    public void raiseLotBetWithLock(Long userId, Long lotId, Long bet) {
        Lot lot = getLotWithLock(lotId);
        User user = getUser(userId);
        lot.setCurrentBet(lot.getCurrentBet() + bet);
        lot.setLastBetOwner(user);
    }

    public long getBetSum() {
        long betSum = 0;
        for (Lot lot : getLots()) {
            betSum += lot.getCurrentBet();
        }
        return betSum;
    }
}
