package com.eshop.dao.interfaces;

import com.eshop.dao.BaseDao;
import com.eshop.entities.Order.Order;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    public List<Order> getOrdersByUserId(int userId);
}
