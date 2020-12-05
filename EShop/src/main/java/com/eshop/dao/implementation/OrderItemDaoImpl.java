package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.OrderItemDao;
import com.eshop.entities.Order.OrderItem;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {
    private final Logger logger = Logger.getLogger(OrderItemDaoImpl.class);
    private final Connection connection;

    public OrderItemDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<OrderItem> getAll() throws DaoException {
        List<OrderItem> orderItems = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM order_items";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            orderItems = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orderItems;
    }

    @Override
    public OrderItem getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        OrderItem orderItem = null;

        try {
            String query = "SELECT * FROM order_items WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<OrderItem> orderItems = parseToList(resultSet);

            if (orderItems.size() > 0) {
                orderItem = orderItems.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting order_item by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return orderItem;
    }

    @Override
    public void add(OrderItem orderItem) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO order_items (order_id,product_id,quantity) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getProductId());
            preparedStatement.setInt(3, orderItem.getQuantity());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new order_item insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(OrderItem orderItem) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE order_items SET order_id=?,product_id=?,quantity=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getProductId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setInt(4, orderItem.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with order_item update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM order_items  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_item deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(OrderItem orderItem) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM order_items  WHERE order_id=?,product_id=?,quantity=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getProductId());
            preparedStatement.setInt(3, orderItem.getQuantity());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_item deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<OrderItem> parseToList(ResultSet resultSet) throws DaoException {
        List<OrderItem> orderItems = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int orderId = resultSet.getInt("order_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");

                orderItems.add(new OrderItem(id, orderId, productId, quantity));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with order_items parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return orderItems;
    }
}
