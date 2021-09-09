package com.es.phoneshop.model.product;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static com.es.phoneshop.model.product.ProductDaoUtils.*;
import static org.junit.Assert.*;

public class ProductDaoUtilsTest {

    @Test
    public void areStringsContainsCommonWords_null() {
        String str = "Samsung Apple Asus";
        assertTrue(areStringsContainsCommonWords(null, str));
    }

    @Test
    public void areStringsContainsCommonWords_true() {
        String str1 = "Samsung Apple Asus";
        String str2 = "asUs galaxy iphone JAVA";

        assertTrue(areStringsContainsCommonWords(str1, str2));
    }

    @Test
    public void areStringsContainsCommonWords_false() {
        String str1 = "Samsung Apple Asus";
        String str2 = "usus galaxy iphone JAVA";

        assertFalse(areStringsContainsCommonWords(str1, str2));
    }

    @Test
    public void areStringsContainsCommonWords_oneEmpty() {
        String str1 = "";
        String str2 = "asUs galaxy iphone JAVA";

        assertFalse(areStringsContainsCommonWords(str1, str2));
    }

    @Test
    public void compareByMatchingWordsNum_equals() {
        String str1 = "Samsung Galaxy S";
        String str2 = "SamsuNg S III Galaxy ";
        String query = "sAmSunG s";

        assertEquals(0, compareByMatchingWordsNum(str1, str2, query));
    }

    @Test
    public void compareByMatchingWordsNum_notEquals() {
        String str1 = "Samsung Galaxy S";
        String str2 = "SamsuNg Galaxy";
        String query = "sAmSunG s";

        assertEquals(-1, compareByMatchingWordsNum(str1, str2, query));
    }

    @Test
    public void compareByMatchingWordsNum_queryNull() {
        String str1 = "Samsung Galaxy S";
        String str2 = "SamsuNg Galaxy";

        assertEquals(0, compareByMatchingWordsNum(str1, str2, null));
    }

    @Test
    public void compareForSortingFieldOrder_description() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        assertTrue(compareForSortingFieldOrder(product1, product2, SortField.description, SortOrder.asc) < 0);
    }

    @Test
    public void compareForSortingFieldOrder_price() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        assertTrue(compareForSortingFieldOrder(product1, product2, SortField.price, SortOrder.desc) > 0);
    }

    @Test
    public void compareForSortingFieldOrder_null() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        assertEquals(0, compareForSortingFieldOrder(product1, product2, SortField.price, null));
    }

}