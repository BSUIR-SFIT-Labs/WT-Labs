package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.UserRoleDao;
import com.eshop.entities.UserAccount.UserRole;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImpl implements UserRoleDao {
    private final Logger logger = Logger.getLogger(UserRoleDao.class);
    private final Connection connection;

    public UserRoleDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<UserRole> getAll() throws DaoException {
        List<UserRole> userRoles = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM user_roles";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            userRoles = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return userRoles;
    }

    @Override
    public UserRole getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        UserRole userRole = null;

        try {
            String query = "SELECT * FROM user_roles WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<UserRole> userRoles = parseToList(resultSet);

            if (userRoles.size() > 0) {
                userRole = userRoles.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting user_role by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return userRole;
    }

    @Override
    public void add(UserRole userRole) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO user_roles (user_id,role_id) VALUES(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new user_role insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(UserRole userRole) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE user_roles SET user_id=?,role_id=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());
            preparedStatement.setInt(3, userRole.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with user_role update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM user_roles  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with user_roles deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(UserRole userRole) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM user_roles  WHERE user_id=? AND role_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with user_role deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<UserRole> parseToList(ResultSet resultSet) throws DaoException {
        List<UserRole> userRoles = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int roleId = resultSet.getInt("role_id");

                userRoles.add(new UserRole(userId, roleId));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with user_roles parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return userRoles;
    }
}
