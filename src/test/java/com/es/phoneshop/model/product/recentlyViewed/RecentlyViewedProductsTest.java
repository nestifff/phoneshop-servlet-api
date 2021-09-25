package com.es.phoneshop.model.product.recentlyViewed;

import com.es.phoneshop.model.product.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class RecentlyViewedProductsTest {

    private RecentlyViewedProducts recentlyProducts;

    @Before
    public void init() {
        recentlyProducts = new RecentlyViewedProducts();
    }

    @Test
    public void addRecentlyViewed_1() {
        Product product = getProducts().get(0);
        recentlyProducts.addRecentlyViewed(product);

        assertEquals(1, recentlyProducts.getRecentlyViewed().size());
        assertTrue(recentlyProducts.getRecentlyViewed().contains(product));
    }

    @Test
    public void addRecentlyViewed_11() {
        Product product = getProducts().get(0);
        recentlyProducts.addRecentlyViewed(product);
        recentlyProducts.addRecentlyViewed(product);

        assertEquals(1, recentlyProducts.getRecentlyViewed().size());
        assertTrue(recentlyProducts.getRecentlyViewed().contains(product));
    }

    @Test
    public void addRecentlyViewed_3() {
        Product product0 = getProducts().get(0);
        Product product1 = getProducts().get(1);
        Product product2 = getProducts().get(2);

        recentlyProducts.addRecentlyViewed(product0);
        recentlyProducts.addRecentlyViewed(product1);
        recentlyProducts.addRecentlyViewed(product2);

        assertEquals(3, recentlyProducts.getRecentlyViewed().size());
        assertEquals(recentlyProducts.getRecentlyViewed().get(0), product2);
        assertEquals(recentlyProducts.getRecentlyViewed().get(1), product1);
    }

    @Test
    public void addRecentlyViewed_4() {
        Product product0 = getProducts().get(0);
        Product product1 = getProducts().get(1);
        Product product2 = getProducts().get(2);
        Product product3 = getProducts().get(3);

        recentlyProducts.addRecentlyViewed(product0);
        recentlyProducts.addRecentlyViewed(product1);
        recentlyProducts.addRecentlyViewed(product2);
        recentlyProducts.addRecentlyViewed(product3);

        assertEquals(3, recentlyProducts.getRecentlyViewed().size());
        assertEquals(recentlyProducts.getRecentlyViewed().get(0), product3);
        assertFalse(recentlyProducts.getRecentlyViewed().contains(product0));
    }

    @Test
    public void addRecentlyViewed_31() {
        Product product0 = getProducts().get(0);
        Product product1 = getProducts().get(1);
        Product product2 = getProducts().get(2);

        recentlyProducts.addRecentlyViewed(product0);
        recentlyProducts.addRecentlyViewed(product1);
        recentlyProducts.addRecentlyViewed(product2);
        recentlyProducts.addRecentlyViewed(product0);

        assertEquals(3, recentlyProducts.getRecentlyViewed().size());
        assertEquals(recentlyProducts.getRecentlyViewed().get(0), product0);
        assertEquals(recentlyProducts.getRecentlyViewed().get(1), product2);
    }

    private List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");

        list.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of()));
        list.add(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", List.of()));
        list.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", List.of()));
        list.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", List.of()));

        return list;
    }

}