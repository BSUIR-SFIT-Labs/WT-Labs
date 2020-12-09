package com.eshop.services.interfaces;

import com.eshop.entities.Order.Product;
import com.eshop.services.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts() throws ServiceException;

    public void addProduct(String name, String description,
                          BigDecimal price, String pictureUrl) throws ServiceException;

    public int updateProduct(Product product) throws ServiceException;

    public void deleteProduct(int productId) throws ServiceException;
}
