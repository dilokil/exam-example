package ru.croc.model;

import java.math.BigInteger;

public class Product {
    private String id;
    private String productName;
    private String description;
    private double weight;
    private BigInteger cost;

    public Product(String id, String productName, String description, double weight, BigInteger cost) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.weight = weight;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public BigInteger getCost() {
        return cost;
    }

    public void setCost(BigInteger cost) {
        this.cost = cost;
    }
}
