package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;
import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

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
    public void add(Cart cart, Long productId, int newQuantity) {
        synchronized (cart) {

            Product product = productDao.getProduct(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not exist");
            }

            CartItem presentItem = cart.getItems().stream()
                    .filter(item -> productId.equals(item.getProduct().getId()))
                    .findAny()
                    .orElse(null);

            if (presentItem != null) {
                update(cart, productId, presentItem.getQuantity() + newQuantity);

            } else {
                checkPositive(newQuantity);
                checkStockEnough(product, newQuantity);

                CartItem newItem = new CartItem(product, newQuantity);
                cart.getItems().add(newItem);
                product.setStock(product.getStock() - newQuantity);

                recalculateCart(cart);
            }
        }
    }

    @Override
    public void update(Cart cart, Long productId, int newQuantity) {
        synchronized (cart) {

            checkPositive(newQuantity);

            Product product = productDao.getProduct(productId);
            CartItem presentItem = cart.getItems().stream()
                    .filter(item -> productId.equals(item.getProduct().getId()))
                    .findAny()
                    .orElse(null);

            int oldQuality = presentItem.getQuantity();
            if (oldQuality == newQuantity) {
                return;
            }
            if (oldQuality < newQuantity) {
                checkStockEnough(product, newQuantity - oldQuality);
            }

            product.setStock(product.getStock() - (newQuantity - oldQuality));
            presentItem.setQuantity(newQuantity);

            recalculateCart(cart);
        }
    }

    @Override
    public void delete(Cart cart, Long productId) {
        CartItem itemToDelete = cart.getItems().stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .findAny()
                .orElse(null);

        itemToDelete.getProduct().setStock(itemToDelete.getProduct().getStock() + itemToDelete.getQuantity());
        cart.getItems().remove(itemToDelete);

        recalculateCart(cart);
    }

    private void recalculateCart(Cart cart) {
        BigDecimal totalCost = new BigDecimal(0);
        int totalQuantity = 0;
        for (CartItem item: cart.getItems()) {
            totalQuantity += item.getQuantity();
            totalCost = totalCost.add(item.getCost());
        }
        cart.setTotalQuantity(totalQuantity);
        cart.setTotalCost(totalCost);
    }

    private void checkStockEnough(Product product, int quantityToAdd) {
        if (product.getStock() < quantityToAdd) {
            throw new IllegalArgumentException("Stock: " + product.getStock() + " is less then required: " + quantityToAdd);
        }
    }

    private void checkPositive(int quantityToAdd) {
        if (quantityToAdd <= 0) {
            throw new IllegalArgumentException("Quantity must be positive number");
        }
    }

}
