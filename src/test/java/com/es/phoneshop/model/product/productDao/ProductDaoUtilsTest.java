package com.es.phoneshop.model.product.productDao;

import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productSortEnums.SortField;
import com.es.phoneshop.model.product.productSortEnums.SortOrder;
import com.es.phoneshop.testUtils.ProductCreator;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static com.es.phoneshop.model.product.productDao.ProductDaoFindProductsUtils.*;
import static org.junit.Assert.*;

public class ProductDaoUtilsTest {

    @Test
    public void needToAddThisProduct_queryNull() {
        Product product = ProductCreator.getProduct();
        assertTrue(needToAddThisProduct(null, product));
    }

    @Test
    public void needToAddThisProduct_queryEmpty() {
        Product product = ProductCreator.getProduct();
        assertTrue(needToAddThisProduct("", product));
    }

    @Test
    public void needToAddThisProduct_containsMatchingWords() {
        Product product = ProductCreator.getProduct();
        assertTrue(needToAddThisProduct("S", product));
    }

    @Test
    public void needToAddThisProduct_not_Match() {
        Product product = ProductCreator.getProduct();
        assertFalse(needToAddThisProduct("Apple", product));
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
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());

        assertTrue(compareForSortingFieldOrder(product1, product2, SortField.DESCRIPTION, SortOrder.ASC) < 0);
    }

    @Test
    public void compareForSortingFieldOrder_price() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());

        assertTrue(compareForSortingFieldOrder(product1, product2, SortField.PRICE, SortOrder.DESC) > 0);
    }

    @Test
    public void compareForSortingFieldOrder_null() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());

        assertEquals(0, compareForSortingFieldOrder(product1, product2, SortField.PRICE, null));
    }

    @Test
    public void compare_sortNull() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        String query = "GALAxY saMSUNG";
        assertEquals(0, compare(product1, product2, null, null, query));
    }

    @Test
    public void compare_bySort() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        Product product2 = new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", List.of());
        String query = "Samsung Galaxy";
        assertTrue(compare(product1, product2, SortField.DESCRIPTION, SortOrder.ASC, query) < 0);
    }


}