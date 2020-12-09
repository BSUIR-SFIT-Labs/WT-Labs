package com.eshop.services.interfaces;

import com.eshop.entities.UserAccount.User;
import com.eshop.services.exception.ServiceException;

public interface UserService {
    User signIn(String email, String password) throws ServiceException;

    void signUp(User user) throws ServiceException;

    void signOut(String email) throws ServiceException;
}
