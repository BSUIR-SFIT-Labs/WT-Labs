package com.eshop.entities.Order;

public class OrderStatus {
    private int id;
    private int statusCode;
    private String description;

    public OrderStatus(int id, int statusCode, String description) {
        this.id = id;
        this.statusCode = statusCode;
        this.description = description;
    }

    public OrderStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
