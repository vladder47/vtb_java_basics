package com.vtb.java.lesson18.homework;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "current_price")
    private Long currentPrice;

    @OneToMany(mappedBy = "product")
    private List<PurchaseDetails> purchaseDetails;

    public Product() {}

    public Product(String name, Long currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    public void addPurchaseDetails(PurchaseDetails pd) {
        purchaseDetails.add(pd);
    }

    public void info() {
        System.out.printf("Товар Название:%s\tТекущая цена:%d", name, currentPrice);
    }
}
