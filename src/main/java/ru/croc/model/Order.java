package ru.croc.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String id;
    private String userId;
    private LocalDateTime createdDate;
    private List<String> products;

    public Order(String id, String userId, LocalDateTime createdDate, List<String> products) {
        this.id = id;
        this.userId = userId;
        this.createdDate = createdDate;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}
