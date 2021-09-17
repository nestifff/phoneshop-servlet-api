package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;
import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import static com.es.phoneshop.model.product.demoData.DemoDataForProductDaoCreator.fillProductDaoDemoData;
import static org.junit.Assert.*;

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
    public void add_allRight() throws QuantityLessThenZeroException, ProductNotFoundInDaoException, ProductStockLessThenRequiredException {

        Long id = 3L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, stock - 1);

        assertEquals(1, product.getStock());
        assertTrue(cart.getItems().contains(new CartItem(product, stock - 1)));
    }

    @Test
    public void add_repeatedly() throws QuantityLessThenZeroException, ProductNotFoundInDaoException, ProductStockLessThenRequiredException {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 2);
        cartService.add(cart, id, 3);

        assertEquals(stock - 5, product.getStock());
        assertTrue(cart.getItems().contains(new CartItem(product, 5)));
    }

    @Test(expected = ProductStockLessThenRequiredException.class)
    public void add_stockLessThenRequired() throws QuantityLessThenZeroException, ProductNotFoundInDaoException, ProductStockLessThenRequiredException {

        Long id = 1L;
        cartService.add(cart, id, 2000000);
    }

    @Test(expected = ProductNotFoundInDaoException.class)
    public void add_productNotExist() throws QuantityLessThenZeroException, ProductNotFoundInDaoException, ProductStockLessThenRequiredException {

        Long id = 1000000L;
        cartService.add(cart, id, 2);
    }

    @Test(expected = QuantityLessThenZeroException.class)
    public void add_lessThenZero() throws QuantityLessThenZeroException, ProductNotFoundInDaoException, ProductStockLessThenRequiredException {

        cartService.add(cart, 1L, -2);
    }

}