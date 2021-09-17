package com.es.phoneshop.model.cartService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Long productId, int quantity) throws ProductNotFoundInDaoException, ProductStockLessThenRequiredException, QuantityLessThenZeroException;
}
