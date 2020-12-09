package com.eshop.services.interfaces;

import com.eshop.entities.Order.Order;
import com.eshop.services.exception.ServiceException;

import java.util.List;

public interface OrderService {
    public List<Order> getAllUserOrders(int userId) throws ServiceException;

    public int createOrder(int userId, List<Integer> productIds) throws ServiceException;
}
