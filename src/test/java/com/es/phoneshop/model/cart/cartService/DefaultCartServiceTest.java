package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import static com.es.phoneshop.model.product.demoData.DemoDataForProductDaoCreator.fillProductDaoDemoData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class DefaultCartServiceTest {

    private static DefaultCartService cartService;
    private static ProductDao productDao;
    private Cart cart;

    @Before
    public void init() {
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        fillProductDaoDemoData(productDao);
        cart = new Cart();
    }

    @Test
    public void getInstance_returnTheSame() {
        assertSame(cartService, DefaultCartService.getInstance());
    }

    @Test
    public void add_allRight(){

        Long id = 3L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, stock - 1);

        assertEquals(1, product.getStock());
        assertEquals(cart.getItem(id).getQuantity(), stock - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_stockLessThenRequired(){

        Long id = 1L;
        cartService.add(cart, id, 2000000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_productNotExist()  {

        Long id = 1000000L;
        cartService.add(cart, id, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_lessThenZero() {

        cartService.add(cart, 1L, -2);
    }

    @Test
    public void add_thenUpdate() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 2);
        cartService.add(cart, id, 3);

        int totalCost = product.getPrice().intValue() * 5;

        assertEquals(stock - 5, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(5, cart.getItem(id).getQuantity());
    }


    @Test(expected = IllegalArgumentException.class)
    public void add_thenUpdate_outOfStock() {
        Long id = 1L;

        cartService.add(cart, id, 2);
        cartService.update(cart, id, 3000);
    }

    @Test
    public void add_thenUpdate_decreaseQuantity() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.update(cart, id, 3);

        int totalCost = product.getPrice().intValue() * 3;

        assertEquals(stock - 3, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(3, cart.getItem(id).getQuantity());
    }

    @Test
    public void add_thenUpdate_notChangeQuantity() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.update(cart, id, 5);

        int totalCost = product.getPrice().intValue() * 5;

        assertEquals(stock - 5, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(5, cart.getItem(id).getQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_thenUpdate_illegalQuantity() {
        Long id = 1L;

        cartService.add(cart, id, 5);
        cartService.update(cart, id, -4);
    }

}