package com.eshop.entities.Order;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Integer id;
    private Integer userId;
    private List<OrderItem> orderItems;
    private LocalDate orderDate;
    private Integer orderStatusId;

    public Order(Integer id, Integer userId, List<OrderItem> orderItems,
                 LocalDate orderDate, Integer orderStatusId) {
        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.orderStatusId = orderStatusId;
    }

    public Order(Integer userId, List<OrderItem> orderItems,
                 LocalDate orderDate, Integer orderStatusId) {
        this.userId = userId;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.orderStatusId = orderStatusId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
