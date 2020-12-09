package com.eshop.dao.interfaces;

import com.eshop.dao.BaseDao;
import com.eshop.dao.exception.DaoException;
import com.eshop.entities.UserAccount.User;

public interface UserDao extends BaseDao<User> {
    public User getUserByEmail(String email) throws DaoException;
}
