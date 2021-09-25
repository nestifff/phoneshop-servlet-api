package com.es.phoneshop.model.cart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private final List<CartItem> items;
    private int totalCost = 0;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addNewItem(CartItem item, int quantity) {
        item.setQuantity(quantity);
        items.add(item);
        totalCost += item.getCost();
    }

    public void changeItemQuantity(CartItem item, int newQuantity) {
        totalCost -= item.getCost();
        item.setQuantity(newQuantity);
        totalCost += newQuantity * item.getProduct().getPrice().intValue();
    }

    public CartItem getItem(Long productId) {
        return items.stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .findAny()
                .orElse(null);
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

            sb.append("<br>   " + i + ". ");
            sb.append(items.get(i - 1).getProduct().getDescription() + ", quantity: " + items.get(i - 1).getQuantity());
            sb.append(", cost: " + items.get(i - 1).getCost());
        }
        sb.append("<br>Total cost: " + totalCost);
        return sb.toString();
    }

}
