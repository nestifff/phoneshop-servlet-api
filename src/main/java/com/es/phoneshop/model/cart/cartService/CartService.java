package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Long productId, int newQuantity);

    void update(Cart cart, Long productId, int newQuantity);

    boolean delete(Cart cart, Long productId);

    void clearCart(HttpServletRequest request, Cart cart);
}



