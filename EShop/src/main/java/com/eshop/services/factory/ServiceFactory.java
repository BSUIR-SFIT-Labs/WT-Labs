package com.eshop.services.factory;

import com.eshop.services.implementation.RoleServiceImpl;
import com.eshop.services.implementation.UserServiceImpl;
import com.eshop.services.interfaces.RoleService;
import com.eshop.services.interfaces.UserService;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl();
    private final RoleService roleService = new RoleServiceImpl();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
