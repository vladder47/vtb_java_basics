package com.vtb.java.lesson18.homework;

import org.hibernate.Session;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
        List<Product> products = new ArrayList<>();
        Client client = getClient(name);
        TypedQuery<PurchaseDetails> query = session.createQuery("SELECT p FROM PurchaseDetails p WHERE p.client = :client", PurchaseDetails.class);
        query.setParameter("client", client);
        for (PurchaseDetails pd : query.getResultList()) {
            products.add(pd.getProduct());
        }
        return products;
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
        List<Client> clients = new ArrayList<>();
        Product product = getProduct(name);
        TypedQuery<PurchaseDetails> query = session.createQuery("SELECT p FROM PurchaseDetails p WHERE p.product = :product", PurchaseDetails.class);
        query.setParameter("product", product);
        for (PurchaseDetails pd : query.getResultList()) {
            clients.add(pd.getClient());
        }
        return clients;
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
        PurchaseDetails purchaseDetails = new PurchaseDetails(client, product, product.getCurrentPrice());
        session.save(purchaseDetails);
    }
}
