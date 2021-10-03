package com.es.phoneshop.model.cart.domain;

import com.es.phoneshop.model.product.domain.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CartItem implements Serializable, Cloneable {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public BigDecimal getCost() {
        return BigDecimal.valueOf(quantity * product.getPrice().intValue());
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
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
