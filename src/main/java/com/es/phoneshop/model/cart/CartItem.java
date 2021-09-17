package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.Objects;

public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int incr) {
        this.quantity += incr;
    }

    public void decreaseQuantity(int decr) {
        this.quantity -= decr;
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
        return quantity == cartItem.quantity &&
                Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
