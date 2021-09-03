package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsStart() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveNewProduct() {

        Currency usd = Currency.getInstance("USD");
        Product testProduct = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(testProduct);

        assertTrue(testProduct.getId() > 0);
        assertNotNull(productDao.getProduct(testProduct.getId()));
        assertSame(productDao.getProduct(testProduct.getId()), testProduct);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteProduct() {
        Long id = 1L;
        productDao.delete(id);
        productDao.getProduct(id);
    }
}
