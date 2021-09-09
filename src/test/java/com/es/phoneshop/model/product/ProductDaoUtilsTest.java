package com.es.phoneshop.model.product;

import org.junit.Test;

import static com.es.phoneshop.model.product.ProductDaoUtils.areStringsContainsCommonWords;
import static com.es.phoneshop.model.product.ProductDaoUtils.compareByMatchingWordsNum;
import static org.junit.Assert.*;

public class ProductDaoUtilsTest {

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

    @Test(expected = NullPointerException.class)
    public void compareByMatchingWordsNum_queryNull() {
        String str1 = "Samsung Galaxy S";
        String str2 = "SamsuNg Galaxy";
        String query = null;

        compareByMatchingWordsNum(str1, str2, query);
    }

}