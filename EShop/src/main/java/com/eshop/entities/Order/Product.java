package com.eshop.entities.Order;

import java.math.BigDecimal;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String pictureUrl;

    public Product(Integer id, String name, String description, BigDecimal price, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public Product(String name, String description, BigDecimal price, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
