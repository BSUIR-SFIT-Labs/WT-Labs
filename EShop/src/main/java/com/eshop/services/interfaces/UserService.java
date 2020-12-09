package com.eshop.services.interfaces;

import com.eshop.entities.UserAccount.User;
import com.eshop.services.exception.ServiceException;

public interface UserService {
    User signIn(String email, String password) throws ServiceException;

    int signUp(String email, String password, String firstName, String lastName) throws ServiceException;
}
