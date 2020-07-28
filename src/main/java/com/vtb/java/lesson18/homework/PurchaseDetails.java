package com.vtb.java.lesson18.homework;

import javax.persistence.*;

@Entity
@Table(name = "purchases_details")
public class PurchaseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "purchase_cost")
    private Long purchaseCost;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public PurchaseDetails() {}

    public PurchaseDetails(Client client, Product product, Long purchaseCost) {
        this.purchaseCost = purchaseCost;
        this.client = client;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Long purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
