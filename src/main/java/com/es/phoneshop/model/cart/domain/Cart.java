package com.es.phoneshop.model.cart.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {

        int totalCost = 0;
        StringBuilder sb = new StringBuilder("Cart:");

        for (int i = 1; i <= items.size(); ++i) {

            int cost = items.get(i - 1).getProduct().getPrice().intValue() * items.get(i - 1).getQuantity();
            sb.append("<br>   " + i + ". ");
            sb.append(items.get(i - 1).getProduct().getDescription() + ", quantity: " + items.get(i - 1).getQuantity());
            sb.append(", cost: " + cost);
            totalCost += cost;
        }
        sb.append("<br>Total cost: " + totalCost);
        return sb.toString();
    }
}
