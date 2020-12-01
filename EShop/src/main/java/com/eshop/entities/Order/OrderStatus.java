package com.eshop.entities.Order;

public class OrderStatus {
    private Integer id;
    private Integer statusCode;
    private String description;

    public OrderStatus(Integer id, Integer statusCode, String description) {
        this.id = id;
        this.statusCode = statusCode;
        this.description = description;
    }

    public OrderStatus(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
