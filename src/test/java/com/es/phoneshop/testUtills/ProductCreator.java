package com.es.phoneshop.testUtills;

import com.es.phoneshop.model.product.domain.Product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class ProductCreator {

    public static Product getProduct() {
        Currency usd = Currency.getInstance("USD");
        return new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
    }
}
