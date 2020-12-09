package com.eshop.services.interfaces;

import com.eshop.entities.UserAccount.Role;
import com.eshop.services.exception.ServiceException;

import java.util.List;

public interface RoleService {
    List<Role> getUserRoles(int userId) throws ServiceException;
}
