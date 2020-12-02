package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.UserDao;
import com.eshop.entities.UserAccount.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private final Connection connection;

    public UserDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM users";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            users = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return users;
    }

    @Override
    public User getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = parseToList(resultSet);

            if (users.size() > 0) {
                user = users.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting user by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        PreparedStatement preparedStatement = null;
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE email=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = parseToList(resultSet);

            if (users.size() > 0) {
                user = users.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting user by email: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return user;
    }

    @Override
    public void add(User user) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO users (email,password_hash,first_name,last_name) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new user insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE users SET email=?,password_hash=?,first_name=?,last_name=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setInt(5, user.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with user update in DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void deleteById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM users  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with user deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM users  WHERE email=? AND password_hash=?  AND first_name=? AND last_name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with user deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<User> parseToList(ResultSet resultSet) throws DaoException {
        List<User> users = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String passwordHash = resultSet.getString("password_hash");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                users.add(new User(id, email, passwordHash, firstName, lastName));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with users parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return users;
    }
}
