package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;
import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;

import javax.servlet.http.HttpServletRequest;

public class DefaultCartService implements CartService {

    private final static String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";

    private static volatile DefaultCartService instance;

    private ProductDao productDao;

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
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        synchronized (request.getSession()) {

            Cart cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);

            if (cart == null) {
                cart = new Cart();
                request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Long productId, int quantity) throws ProductNotFoundInDaoException, ProductStockLessThenRequiredException, QuantityLessThenZeroException {
        synchronized (cart) {

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
                cart.increaseItemQuantity(presentItem, quantity);
            } else {
                cart.addItem(new CartItem(product, quantity));
            }
            product.setStock(product.getStock() - quantity);
        }
    }
}
