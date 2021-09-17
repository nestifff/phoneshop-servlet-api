package com.es.phoneshop.model.cartService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;

public interface CartService {

    Cart getCart();
    void add(Long productId, int quantity) throws ProductNotFoundInDaoException, ProductStockLessThenRequiredException, QuantityLessThenZeroException;
}
