package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void findProducts_returnProperEmptyQuery() {

        List<Product> testProducts = productDao.findProducts("", null, null);
        boolean haveNotProper = testProducts.stream()
                .anyMatch(it ->
                        it.getPrice() == null
                                || it.getStock() <= 0);

        assertFalse(haveNotProper);
    }

    @Test
    public void findProducts_returnProperWithQuery() {

        String query = "Samsung S";

        List<Product> testProducts = productDao.findProducts(query, null, null);
        boolean haveNotProper = testProducts.stream()
                .anyMatch(it ->
                        it.getPrice() == null || it.getStock() <= 0);

        boolean isFoundedWithMatching = testProducts.stream()
                .allMatch(it ->
                        ProductDaoUtils.areStringsContainsCommonWords(it.getDescription(), "Samsung S"));

        assertFalse(haveNotProper);
        assertTrue(isFoundedWithMatching);
        assertTrue(testProducts.size() != 0);
    }

    @Test
    public void findProducts_returnProperNullQuery() {

        List<Product> testProducts = productDao.findProducts(null, null, null);
        assertTrue(testProducts.size() != 0);
    }

    @Test
    public void findProducts_returnWithSortDescription() {

        List<Product> testProducts = productDao.findProducts("", SortField.description, SortOrder.asc);

        boolean orderIsRight = true;
        Product prev = testProducts.get(0);
        for (int i = 1; i < testProducts.size(); ++i) {
            if (prev.getDescription().compareTo(testProducts.get(i).getDescription()) >= 0) {
                orderIsRight = false;
                break;
            }
        }

        assertTrue(orderIsRight);
    }

    @Test
    public void findProducts_returnWithQuerySortPrice() {

        String query = "Samsung S";

        List<Product> testProducts = productDao.findProducts(query, SortField.price, SortOrder.asc);

        boolean orderIsRight = true;
        Product prev = testProducts.get(0);
        for (int i = 1; i < testProducts.size(); ++i) {
            if (prev.getPrice().compareTo(testProducts.get(i).getPrice()) >= 0) {
                orderIsRight = false;
                break;
            }
        }

        assertTrue(orderIsRight);
        assertTrue(testProducts.size() < productDao.findProducts(null, null, null).size());
    }

    @Test
    public void get_Product() {
        assertNotNull(productDao.getProduct(1L));
    }

    @Test
    public void get_ProductNotExistingId() {
        assertNull(productDao.getProduct(-1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_ProductNullId() {
        assertNotNull(productDao.getProduct(null));
    }

    @Test
    public void save_ProductNullId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        boolean isAdded = productDao.save(testProduct);

        assertTrue(isAdded);
        assertTrue(testProduct.getId() > 0);
        assertSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void save_ProductExistingId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        testProduct.setId(1L);

        boolean isAdded = productDao.save(testProduct);

        assertFalse(isAdded);
        assertEquals(productDao.getProduct(testProduct.getId()), testProduct);
        assertNotSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void save_ProductNotExistingId() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Long id = 1000L;
        testProduct.setId(id);

        boolean isAdded = productDao.save(testProduct);

        assertTrue(isAdded);
        assertTrue(testProduct.getId() > 0);
        assertNotSame(id, testProduct.getId());
        assertSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test
    public void delete_Product() {
        Long id = 1L;

        assertTrue(productDao.delete(id));
        assertNull(productDao.getProduct(id));
    }

    @Test
    public void delete_ProductNotExistingId() {
        Long id = 1000L;
        assertFalse(productDao.delete(id));
    }
}
