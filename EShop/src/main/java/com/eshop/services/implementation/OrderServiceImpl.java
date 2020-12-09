package com.eshop.services.implementation;

import com.eshop.dao.exception.DaoException;
import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.OrderDao;
import com.eshop.dao.interfaces.OrderItemDao;
import com.eshop.dao.interfaces.OrderStatusDao;
import com.eshop.entities.Order.Order;
import com.eshop.entities.Order.OrderItem;
import com.eshop.services.exception.ServiceException;
import com.eshop.services.interfaces.OrderService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final OrderStatusDao orderStatusDao;

    public OrderServiceImpl() {
        this.orderDao = DaoFactory.getInstance().getOrderDao();
        this.orderItemDao = DaoFactory.getInstance().getOrderItemDao();
        this.orderStatusDao = DaoFactory.getInstance().getOrderStatusDao();
    }

    @Override
    public List<Order> getAllUserOrders(int userId) {
        if (userId <= 0) {
            String errorMessage = userId + " - invalid user id.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        List<Order> orders = null;

        try {
            orders = orderDao.getOrdersByUserId(userId);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        return orders;
    }

    @Override
    public int createOrder(int userId, List<Integer> productIds) {
        if (userId <= 0) {
            String errorMessage = userId + " - invalid user id.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (productIds == null) {
            String errorMessage = "Invalid product ids.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Order order = new Order(userId, timestamp, 1);

        int orderId;

        try {
            orderDao.add(order);
            List<Order> userOrders = orderDao.getOrdersByUserId(userId);
            orderId = userOrders.get(userOrders.size() - 1).getId();

            for (int productId : productIds) {
                OrderItem orderItem = new OrderItem(orderId, productId, 1);

                orderItemDao.add(orderItem);
            }
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        return orderId;
    }
}
