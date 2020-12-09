package com.eshop.services.implementation;

import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.RoleDao;
import com.eshop.dao.interfaces.UserRoleDao;
import com.eshop.entities.UserAccount.Role;
import com.eshop.entities.UserAccount.UserRole;
import com.eshop.services.exception.ServiceException;
import com.eshop.services.interfaces.RoleService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private final Logger logger = Logger.getLogger(RoleServiceImpl.class);

    private final UserRoleDao userRoleDao;
    private final RoleDao roleDao;

    public RoleServiceImpl() {
        this.userRoleDao = DaoFactory.getInstance().getUserRoleDao();
        this.roleDao = DaoFactory.getInstance().getRoleDao();
    }

    @Override
    public List<Role> getUserRoles(int userId) throws ServiceException {
        if (userId <= 0) {
            String errorMessage = userId + " - invalid user id.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        List<Role> roles = new ArrayList<Role>();

        List<UserRole> userRoles = userRoleDao.getByUserId(userId);

        for (UserRole userRole: userRoles) {
            roles.add(roleDao.getById(userRole.getRoleId()));
        }

        return roles;
    }
}
