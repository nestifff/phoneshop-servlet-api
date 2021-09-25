package com.es.phoneshop.model.cart.domain;

import com.es.phoneshop.model.product.domain.Product;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {

    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
    }

    public int getCost() {
        return quantity * product.getPrice().intValue();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        product.setStock(product.getStock() + quantity - newQuantity);
        quantity = newQuantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return product.equals(cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }


}
