package com.es.phoneshop.model.cart.domain;

import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import static com.es.phoneshop.model.product.demoData.DemoDataForProductDaoCreator.fillProductDaoDemoData;
import static org.junit.Assert.*;

public class CartTest {

    private static Cart cart;
    private static ProductDao productDao;

    @Before
    public void init() {
        cart = new Cart();
        productDao = ArrayListProductDao.getInstance();
        fillProductDaoDemoData(productDao);
    }

    @Test
    public void addNewItem_allRight() {
        cart.addNewItem(new CartItem(productDao.getProduct(1L)), 3);

        assertEquals(cart.getItems().size(), 1);
        assertTrue(cart.getItems().contains(new CartItem(productDao.getProduct(1L))));
        assertEquals(cart.getTotalCost(), productDao.getProduct(1L).getPrice().intValue() * 3);
    }

    @Test
    public void changeItemQuantity_increase() {
        CartItem item = new CartItem(productDao.getProduct(1L));
        int oldStock = productDao.getProduct(1L).getStock();
        cart.addNewItem(item, 3);
        cart.changeItemQuantity(item, 5);

        assertEquals(cart.getItems().size(), 1);
        assertTrue(cart.getItems().contains(item));
        assertEquals(cart.getTotalCost(), productDao.getProduct(1L).getPrice().intValue() * 5);

        assertEquals(productDao.getProduct(1L).getStock(), oldStock - 5);
    }

    @Test
    public void changeItemQuantity_decrease() {
        CartItem item = new CartItem(productDao.getProduct(1L));
        int oldStock = productDao.getProduct(1L).getStock();
        cart.addNewItem(item, 3);
        cart.changeItemQuantity(item, 1);

        assertEquals(cart.getItems().size(), 1);
        assertTrue(cart.getItems().contains(item));
        assertEquals(cart.getTotalCost(), productDao.getProduct(1L).getPrice().intValue());

        assertEquals(productDao.getProduct(1L).getStock(), oldStock - 1);
    }

    @Test
    public void changeItemQuantity_notChange() {
        CartItem item = new CartItem(productDao.getProduct(1L));
        int oldStock = productDao.getProduct(1L).getStock();
        cart.addNewItem(item, 3);
        cart.changeItemQuantity(item, 3);

        assertEquals(cart.getItems().size(), 1);
        assertTrue(cart.getItems().contains(item));
        assertEquals(cart.getTotalCost(), productDao.getProduct(1L).getPrice().intValue() * 3);

        assertEquals(productDao.getProduct(1L).getStock(), oldStock - 3);
    }

    @Test
    public void getItem_allRight() {
        CartItem item = new CartItem(productDao.getProduct(1L));
        cart.addNewItem(item, 3);

        assertSame(cart.getItem(1L), item);
    }

    @Test
    public void getItem_notFound() {
        CartItem item = new CartItem(productDao.getProduct(1L));
        cart.addNewItem(item, 3);

        assertNull(cart.getItem(10000L));
    }

}