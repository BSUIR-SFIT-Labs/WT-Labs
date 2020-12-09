package com.eshop.services.implementation;

import com.eshop.dao.exception.DaoException;
import com.eshop.dao.factory.DaoFactory;
import com.eshop.dao.interfaces.ProductDao;
import com.eshop.entities.Order.Product;
import com.eshop.services.exception.ServiceException;
import com.eshop.services.interfaces.ProductService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;

    public ProductServiceImpl() {
        this.productDao = DaoFactory.getInstance().getProductDao();
    }

    @Override
    public List<Product> getAllProducts() throws ServiceException {
        List<Product> products = null;

        try {
            products = productDao.getAll();
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        return products;
    }

    @Override
    public void addProduct(String name, String description,
                          BigDecimal price, String pictureUrl) throws ServiceException {
        if (name == null || name.isEmpty()) {
            String errorMessage = "Invalid product name.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (description == null || description.isEmpty()) {
            String errorMessage = "Invalid product description.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (price == null) {
            String errorMessage = "Invalid product price.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        if (pictureUrl == null || pictureUrl.isEmpty()) {
            String errorMessage = "Invalid product pictureUrl.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        Product product = new Product(name, description, price, pictureUrl);

        try {
            productDao.add(product);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }
    }

    @Override
    public int updateProduct(Product product) throws ServiceException {
        if (product == null) {
            String errorMessage = "Invalid product.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            productDao.update(product);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }

        return product.getId();
    }


    @Override
    public void deleteProduct(int productId) throws ServiceException {
        if (productId <= 0) {
            String errorMessage = "Invalid product id.";

            logger.error(errorMessage);
            throw new ServiceException(errorMessage);
        }

        try {
            productDao.deleteById(productId);
        } catch (DaoException ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }
    }
}
