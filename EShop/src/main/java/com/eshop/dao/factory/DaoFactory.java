package com.eshop.dao.factory;

import com.eshop.dao.implementation.*;
import com.eshop.dao.interfaces.*;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final UserDao userDao = new UserDaoImpl();
    private final RoleDao roleDao = new RoleDaoImpl();
    private final UserRoleDao userRoleDao = new UserRoleDaoImpl();

    private final OrderDao orderDao = new OrderDaoImpl();
    private final OrderStatusDao orderStatusDao = new OrderStatusDaoImpl();
    private final ProductDao productDao = new ProductDaoImpl();
    private final OrderItemDao orderItemDao = new OrderItemDaoImpl();

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public UserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public OrderStatusDao getOrderStatusDao() {
        return orderStatusDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public OrderItemDao getOrderItemDao() {
        return orderItemDao;
    }
}
