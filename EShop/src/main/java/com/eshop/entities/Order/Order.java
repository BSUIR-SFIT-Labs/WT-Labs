package com.eshop.entities.Order;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Order {
    private int id;
    private int userId;
    private Timestamp orderDate;
    private int orderStatusId;

    public Order(int id, int userId, Timestamp orderDate, int orderStatusId) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatusId = orderStatusId;
    }

    public Order(int userId, Timestamp orderDate, int orderStatusId) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderStatusId = orderStatusId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
