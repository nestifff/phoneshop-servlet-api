package com.es.phoneshop.model.cartService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productDao.ArrayListProductDao;
import com.es.phoneshop.model.productDao.ProductDao;

public class DefaultCartService implements CartService {

    private static volatile DefaultCartService instance;

    private ProductDao productDao;
    private Cart cart;

    public static DefaultCartService getInstance() {

        if (instance == null) {
            synchronized (DefaultCartService.class) {

                if (instance == null) {
                    instance = new DefaultCartService();
                }
            }
        }
        return instance;
    }

    private DefaultCartService() {
        productDao = ArrayListProductDao.getInstance();
        cart = new Cart();
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public void add(Long productId, int quantity) throws ProductNotFoundInDaoException, ProductStockLessThenRequiredException, QuantityLessThenZeroException {

        if (quantity <= 0) {
            throw new QuantityLessThenZeroException("Quantity must be positive number");
        }

        Product product = productDao.getProduct(productId);
        if (product == null) {
            throw new ProductNotFoundInDaoException("Product not exist");
        }
        if (product.getStock() < quantity) {
            throw new ProductStockLessThenRequiredException("Stock: " + product.getStock() + " is less then required: " + quantity);
        }

        CartItem presentItem = cart.getItems().stream().filter(it -> product.equals(it.getProduct())).findAny().orElse(null);
        if (presentItem != null) {
            presentItem.increaseQuantity(quantity);
        } else {
            cart.getItems().add(new CartItem(product, quantity));
        }
        product.setStock(product.getStock() - quantity);
    }
}
