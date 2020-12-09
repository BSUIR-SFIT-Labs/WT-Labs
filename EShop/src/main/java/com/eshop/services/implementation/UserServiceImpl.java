package com.eshop.services.implementation;

import com.eshop.dao.exception.DaoException;
import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.UserDao;
import com.eshop.entities.UserAccount.User;
import com.eshop.services.exception.ServiceException;
import com.eshop.services.interfaces.UserService;
import com.eshop.utils.HashUtility;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        User user = null;

        try {
            user = userDao.getUserByEmail(email);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        String passwordHash = HashUtility.getHash(password);

        if (user == null || user.getPasswordHash() != passwordHash) {
           return null;
        }

        return user;
    }

    @Override
    public int signUp(String email, String password, String firstName, String lastName) throws ServiceException {
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

        if (firstName == null || firstName.isEmpty()) {
            String errorMessage = "Invalid firstName.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (lastName == null || lastName.isEmpty()) {
            String errorMessage = "Invalid lastName.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (userDao.getUserByEmail(email) != null) {
            String errorMessage = "This user already exists.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        String passwordHash = HashUtility.getHash(password);
        User user = new User(email, passwordHash, firstName, lastName);

        try {
            userDao.add(user);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        return userDao.getUserByEmail(email).getId();
    }

    private static boolean isValidEmail(String email) {
        String emailPatternStr = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern emailPattern = Pattern.compile(emailPatternStr);

        Matcher matcher = emailPattern.matcher(email);

        return matcher.matches();
    }
}
