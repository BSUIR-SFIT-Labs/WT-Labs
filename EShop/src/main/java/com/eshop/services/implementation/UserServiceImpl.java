package com.eshop.services.implementation;

import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.UserDao;
import com.eshop.entities.UserAccount.User;
import com.eshop.services.exception.ServiceException;
import com.eshop.services.interfaces.UserService;
import com.eshop.utils.HashUtility;
import org.apache.log4j.Logger;
//import sun.jvm.hotspot.debugger.AddressException;

import javax.mail.internet.InternetAddress;

public class UserServiceImpl implements UserService {
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = DaoFactory.getInstance().getUserDao();
    }

    @Override
    public User signIn(String email, String password) throws ServiceException {
        if (!isValidEmail(email)) {
            String errorMessage = "Invalid email.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (password == null || password.isEmpty()) {
            String errorMessage = "Invalid password.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        User user = userDao.getUserByEmail(email);
        String passwordHash = HashUtility.getHash(password);

        if (user == null || user.getPasswordHash() != passwordHash) {
           return null;
        }

        return user;
    }

    @Override
    public void signUp(User user) throws ServiceException {

    }

    @Override
    public void signOut(String email) throws ServiceException {

    }

    private static boolean isValidEmail(String email) {
        boolean isValidEmail = true;

        /*try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException | javax.mail.internet.AddressException ex) {
            isValidEmail = false;
        }*/

        return isValidEmail;
    }
}
