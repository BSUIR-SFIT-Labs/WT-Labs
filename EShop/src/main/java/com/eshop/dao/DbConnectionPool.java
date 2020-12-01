package com.eshop.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnectionPool {
    private static DbConnectionPool instance = null;

    private DbConnectionPool() {
    }

    public static DbConnectionPool getInstance() {
        if (instance == null) {
            instance = new DbConnectionPool();
        }

        return instance;
    }

    public Connection getConnection() {
        Context context;
        Connection connection = null;

        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/eShopDbPool");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
