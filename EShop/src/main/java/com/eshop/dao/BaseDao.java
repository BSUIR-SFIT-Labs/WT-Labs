package com.eshop.dao;

import com.eshop.dao.exception.DaoException;
import com.eshop.entities.UserAccount.User;

import java.sql.ResultSet;
import java.util.List;

public interface BaseDao<TEntity> {
    public List<TEntity> getAll() throws DaoException;

    public TEntity getById(int id) throws DaoException;

    public void add(TEntity entity) throws DaoException;

    public void update(TEntity entity) throws DaoException;

    public void deleteById(int id) throws DaoException;

    public void delete(TEntity entity) throws DaoException;

    public List<TEntity> parseToList(ResultSet resultSet) throws DaoException;
}
