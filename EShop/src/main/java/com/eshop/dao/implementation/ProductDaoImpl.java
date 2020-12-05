package com.eshop.dao.implementation;

import com.eshop.dao.DbConnectionPool;
import com.eshop.dao.exception.DaoException;
import com.eshop.dao.interfaces.ProductDao;
import com.eshop.entities.Order.Product;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private final Logger logger = Logger.getLogger(ProductDaoImpl.class);
    private final Connection connection;

    public ProductDaoImpl() {
        this.connection = DbConnectionPool.getInstance().getConnection();
    }

    @Override
    public List<Product> getAll() throws DaoException {
        List<Product> products = null;
        PreparedStatement preparedStatement = null;

        try {
            String query = "SELECT * FROM products";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            products = parseToList(resultSet);
        } catch (Exception ex) {
            String errorMessage = "There are problems with getting all records: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return products;
    }

    @Override
    public Product getById(int id) throws DaoException {
        PreparedStatement preparedStatement = null;
        Product product = null;

        try {
            String query = "SELECT * FROM products WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> products = parseToList(resultSet);

            if (products.size() > 0) {
                product = products.get(0);
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems getting product by id: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }

        return product;
    }

    @Override
    public void add(Product product) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO products (name,description,price,picture_url) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setString(4, product.getPictureUrl());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with new product insertion to DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void update(Product product) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE products SET name=?,description=?,price=?,picture_url=? WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setString(4, product.getPictureUrl());
            preparedStatement.setInt(5, product.getId());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String errorMessage = "There are problems with product update in DB: " + ex.getLocalizedMessage();

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
            String query = "DELETE FROM products  WHERE id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with product deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public void delete(Product product) throws DaoException {
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM products  WHERE name=?,description=?,price=?,pictire_url=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            preparedStatement.setString(4, product.getPictureUrl());

            preparedStatement.execute();
        } catch (SQLException ex) {
            String errorMessage = "There are problems with product deleting from DB: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        } finally {
            DbConnectionPool.closeStatement(preparedStatement, logger);
        }
    }

    @Override
    public List<Product> parseToList(ResultSet resultSet) throws DaoException {
        List<Product> products = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                BigDecimal price = resultSet.getBigDecimal("price");
                String pictureUrl = resultSet.getString("picture_url");

                products.add(new Product(id, name, description, price, pictureUrl));
            }
        } catch (SQLException ex) {
            String errorMessage = "There are problems with products parsing: " + ex.getLocalizedMessage();

            logger.error(errorMessage);
            throw new DaoException(errorMessage);
        }

        return products;
    }
}
