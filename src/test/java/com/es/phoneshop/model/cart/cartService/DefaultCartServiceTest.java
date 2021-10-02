package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;
import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

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
    public void add_allRight() {

        Long id = 3L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, stock - 1);

        assertEquals(1, product.getStock());
        assertEquals(
                stock - 1,
                cart.getItems().stream()
                        .filter(item -> id.equals(item.getProduct().getId()))
                        .findAny()
                        .orElse(null).getQuantity()

        );
        assertEquals(stock - 1, cart.getTotalQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_stockLessThenRequired() {

        Long id = 1L;
        cartService.add(cart, id, 2000000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_productNotExist() {

        Long id = 1000000L;
        cartService.add(cart, id, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_lessThenZero() {

        cartService.add(cart, 1L, -2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void update_outOfStock() {
        Long id = 1L;

        cartService.add(cart, id, 2);
        cartService.update(cart, id, 3000);
    }

    @Test
    public void update_decreaseQuantity() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.update(cart, id, 3);

        BigDecimal totalCost = BigDecimal.valueOf(product.getPrice().intValue() * 3);

        assertEquals(stock - 3, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(
                3,
                cart.getItems().stream()
                        .filter(item -> id.equals(item.getProduct().getId()))
                        .findAny()
                        .orElse(null).getQuantity()
        );
        assertEquals(3, cart.getTotalQuantity());
    }

    @Test
    public void update_increaseQuantity() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.update(cart, id, 6);

        BigDecimal totalCost = BigDecimal.valueOf(product.getPrice().intValue() * 6);

        assertEquals(stock - 6, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(6,
                cart.getItems().stream()
                        .filter(item -> id.equals(item.getProduct().getId()))
                        .findAny()
                        .orElse(null).getQuantity()
        );
        assertEquals(6, cart.getTotalQuantity());
    }


    @Test
    public void update_notChangeQuantity() {

        Long id = 1L;
        Product product = productDao.getProduct(id);
        int stock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.update(cart, id, 5);

        BigDecimal totalCost = BigDecimal.valueOf(product.getPrice().intValue() * 5);

        assertEquals(stock - 5, product.getStock());
        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(5,
                cart.getItems().stream()
                        .filter(item -> id.equals(item.getProduct().getId()))
                        .findAny()
                        .orElse(null).getQuantity()
        );
        assertEquals(5, cart.getTotalQuantity());
    }

    @Test
    public void AddUpdate_severalAllRight() {

        Long id1 = 1L;
        Product product1 = productDao.getProduct(id1);

        Long id2 = 5L;
        Product product2 = productDao.getProduct(id2);

        cartService.add(cart, id1, 2);
        cartService.add(cart, id1, 3);
        cartService.add(cart, id2, 3);

        BigDecimal totalCost = BigDecimal.valueOf(product1.getPrice().intValue() * 5 + product2.getPrice().intValue() * 3);

        assertEquals(cart.getTotalCost(), totalCost);
        assertEquals(cart.getItems().size(), 2);
        assertEquals(8, cart.getTotalQuantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_illegalQuantity() {
        Long id = 1L;

        cartService.add(cart, id, 5);
        cartService.update(cart, id, -4);
    }

    @Test
    public void delete_allRight() {
        Long id = 1L;
        Product product = productDao.getProduct(id);
        int startStock = product.getStock();
        cartService.add(cart, id, 5);
        cartService.delete(cart, id);

        assertTrue(cart.getItems().isEmpty());
        assertEquals(product.getStock(), startStock);
        assertEquals(cart.getTotalCost(), BigDecimal.valueOf(0));
        assertEquals(0, cart.getTotalQuantity());
    }

    @Test
    public void delete_notExist() {
        Long id = 100000L;
        List<CartItem> cartItemsOld = cart.getItems();
        cartService.delete(cart, id);

        assertEquals(cartItemsOld, cart.getItems());
    }

}