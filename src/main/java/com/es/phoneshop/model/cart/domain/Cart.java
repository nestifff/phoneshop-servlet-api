package com.es.phoneshop.model.cart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private final List<CartItem> items;
    private int totalCost;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(CartItem item) {
        items.add(item);
        totalCost += item.getQuantity() * item.getProduct().getPrice().intValue();
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public int getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Cart:");

        for (int i = 1; i <= items.size(); ++i) {

            int cost = items.get(i - 1).getProduct().getPrice().intValue() * items.get(i - 1).getQuantity();
            sb.append("<br>   " + i + ". ");
            sb.append(items.get(i - 1).getProduct().getDescription() + ", quantity: " + items.get(i - 1).getQuantity());
            sb.append(", cost: " + cost);
        }
        sb.append("<br>Total cost: " + totalCost);
        return sb.toString();
    }

    public void increaseItemQuantity(CartItem item, int toAdd) {
        totalCost += toAdd * item.getProduct().getPrice().intValue();
        item.increaseQuantity(toAdd);
    }
}
