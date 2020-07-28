package com.vtb.java.lesson18.homework;

import org.hibernate.Session;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DataBaseAPI {
    private final HibernateSessionFactory sessionFactory;
    private Session session;

    public DataBaseAPI(HibernateSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = null;
    }

    public void beginTransaction() {
        session = sessionFactory.getSession();
        session.beginTransaction();
    }

    public void createNativeQuery(String query) {
        session.createNativeQuery(query).executeUpdate();
    }

    public void commitAndClose() {
        session.getTransaction().commit();
        session.close();
    }

    public Client getClient(String name) {
        Query query = session.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class);
        query.setParameter("name", name);
        return (Client) query.getSingleResult();
    }

    public Client getClient(Long id) {
        return session.get(Client.class, id);
    }

    public Product getProduct(String name) {
        Query query = session.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);
        return (Product) query.getSingleResult();
    }

    public Product getProduct(Long id) {
        return session.get(Product.class, id);
    }

    public List<Product> getProductsByConsumer(String name) {
        Client client = getClient(name);
        return client.getProducts();
    }

    public List<Long> getPurchasesCost(String name) {
        Client client = getClient(name);
        List<Long> costs = new ArrayList<>();
        for (PurchaseDetails p : client.getPurchaseDetails()) {
            costs.add(p.getPurchaseCost());
        }
        return costs;
    }

    public List<Client> getConsumersByProductTitle(String name) {
        Product product = getProduct(name);
        return product.getClients();
    }

    public void deleteClient(String name) {
        Query query = session.createQuery("DELETE FROM Client c WHERE c.name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    public void deleteProduct(String name) {
        Query query = session.createQuery("DELETE FROM Product p WHERE p.name = :name");
        query.setParameter("name", name);
        query.executeUpdate();
    }

    public void buyProduct(Long clientId, Long productId) {
        Client client = getClient(clientId);
        Product product = getProduct(productId);
        Purchase purchase = new Purchase(clientId, productId);
        session.save(purchase);
        PurchaseDetails purchaseDetails = new PurchaseDetails(client, product, product.getCurrentPrice());
        session.save(purchaseDetails);
    }
}
