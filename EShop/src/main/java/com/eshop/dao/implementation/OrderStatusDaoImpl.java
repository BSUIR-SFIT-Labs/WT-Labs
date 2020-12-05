package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.OrderStatusDao;
import com.eshop.entities.Order.OrderStatus;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDaoImpl implements OrderStatusDao {
    private final Logger logger = Logger.getLogger(OrderStatusDaoImpl.class);
    private final Connection connection;

    public OrderStatusDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<OrderStatus> getAll() throws DaoException {
        List<OrderStatus> orderStatuses = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM statuses";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            orderStatuses = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orderStatuses;
    }

    @Override
    public OrderStatus getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        OrderStatus orderStatus = null;

        try {
            String query = "SELECT * FROM statuses WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<OrderStatus> orderStatuses = parseToList(resultSet);

            if (orderStatuses.size() > 0) {
                orderStatus = orderStatuses.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting order_status by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orderStatus;
    }

    @Override
    public void add(OrderStatus orderStatus) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO statuses (code,description) VALUES(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderStatus.getStatusCode());
            preparedStatement.setString(2, orderStatus.getDescription());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new order_status insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(OrderStatus orderStatus) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE statuses SET code=?,description=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderStatus.getStatusCode());
            preparedStatement.setString(2, orderStatus.getDescription());
            preparedStatement.setInt(3, orderStatus.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with order_status update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM statuses  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_status deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(OrderStatus orderStatus) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM statuses  WHERE code=?,description=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderStatus.getStatusCode());
            preparedStatement.setString(2, orderStatus.getDescription());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_status deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<OrderStatus> parseToList(ResultSet resultSet) throws DaoException {
        List<OrderStatus> orderStatuses = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int statusCode = resultSet.getInt("code");
                String description = resultSet.getString("description");

                orderStatuses.add(new OrderStatus(id, statusCode, description));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_statuses parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return orderStatuses;
    }
}
