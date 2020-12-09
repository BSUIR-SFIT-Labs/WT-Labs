package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.OrderDao;
import com.eshop.entities.Order.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private final Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private final Connection connection;

    public OrderDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<Order> getAll() throws DaoException {
        List<Order> orders = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM orders";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            orders = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orders;
    }

    @Override
    public Order getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        Order order = null;

        try {
            String query = "SELECT * FROM orders WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> orders = parseToList(resultSet);

            if (orders.size() > 0) {
                order = orders.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting order by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return order;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM orders WHERE user_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            orders = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting order by user_id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orders;
    }

    @Override
    public void add(Order order) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO orders (user_id,order_date,order_status_id) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setTimestamp(2, order.getOrderDate());
            preparedStatement.setInt(3, order.getOrderStatusId());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new order insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(Order order) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE orders SET user_id=?,order_date=?,order_status_id=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setTimestamp(2, order.getOrderDate());
            preparedStatement.setInt(3, order.getOrderStatusId());
            preparedStatement.setInt(4, order.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with order update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM orders  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(Order order) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM orders  WHERE user_id=?,order_date=?,order_status_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setTimestamp(2, order.getOrderDate());
            preparedStatement.setInt(3, order.getOrderStatusId());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<Order> parseToList(ResultSet resultSet) throws DaoException {
        List<Order> orders = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                Timestamp orderDate = resultSet.getTimestamp("order_date");
                int orderStatusId = resultSet.getInt("order_status_id");

                orders.add(new Order(id, userId, orderDate, orderStatusId));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with orders parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return orders;
    }
}
