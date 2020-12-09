package com.eshop.dao.interfaces;

import com.eshop.dao.BaseDao;
import com.eshop.dao.exception.DaoException;
import com.eshop.entities.UserAccount.UserRole;

import java.util.List;

public interface UserRoleDao extends BaseDao<UserRole> {
    public List<UserRole> getByUserId(int userId) throws DaoException;
}
