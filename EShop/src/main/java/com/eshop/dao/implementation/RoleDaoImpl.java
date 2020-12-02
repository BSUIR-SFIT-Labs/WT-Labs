package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.RoleDao;
import com.eshop.entities.UserAccount.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private final Logger logger = Logger.getLogger(RoleDao.class);
    private final Connection connection;

    public RoleDaoImpl(Connection connection) {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<Role> getAll() throws DaoException {
        List<Role> roles = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM roles";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            roles = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return roles;
    }

    @Override
    public Role getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        Role role = null;

        try {
            String query = "SELECT * FROM roles WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roles = parseToList(resultSet);

            if (roles.size() > 0) {
                role = roles.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting role by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return role;
    }

    @Override
    public void add(Role role) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO roles (name) VALUES(?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new role insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(Role role) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE roles SET name=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with role update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM roles  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with role deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(Role role) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM roles  WHERE name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with role deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<Role> parseToList(ResultSet resultSet) throws DaoException {
        List<Role> roles = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                roles.add(new Role(id, name));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with roles parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return roles;
    }
}
