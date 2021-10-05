package com.es.phoneshop.model.cart.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private  List<CartItem> items;
    private BigDecimal totalCost;
    private int totalQuantity;

    public Cart() {
        this.items = new ArrayList<>();
        totalCost = new BigDecimal(0);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal subtotal) {
        this.totalCost = subtotal;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Cart:");

        for (int i = 1; i <= items.size(); ++i) {

            sb.append("<br>   " + i + ". ");
            sb.append(items.get(i - 1).getProduct().getDescription() + ", quantity: " + items.get(i - 1).getQuantity());
            sb.append(", cost: " + items.get(i - 1).getCost());
        }
        sb.append("<br>Total cost: " + totalCost);
        return sb.toString();
    }


}
