package com.es.phoneshop.testUtils;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;
import com.es.phoneshop.model.product.domain.PriceHistoryItem;
import com.es.phoneshop.model.product.domain.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class CartOrderCreator {

    public static Cart anyFilledCart() {
        Cart cart = new Cart();
        List<Product> items = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");

        List<PriceHistoryItem> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2010, 11, 12), BigDecimal.valueOf(130), usd));

        items.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 1, 10), BigDecimal.valueOf(200), usd));
        items.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 1, 10), BigDecimal.valueOf(200), usd));
        items.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));

        cart.setItems(items.stream()
                .map(it -> new CartItem(it, 1))
                .collect(Collectors.toList())
        );

        cart.setTotalQuantity(3);
        cart.setTotalCost(BigDecimal.valueOf(600));

        return cart;
    }
}
