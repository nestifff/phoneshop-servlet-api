package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.domain.Cart;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.es.phoneshop.testUtils.CartOrderCreator.anyFilledCart;
import static org.junit.Assert.*;

public class DefaultOrderServiceTest {

    private OrderService orderService;

    @Before
    public void init() {
        orderService = DefaultOrderService.getInstance();
    }

    @Test
    public void getInstance_returnSame() {
        assertSame(orderService, DefaultOrderService.getInstance());
    }

    @Test
    public void getOrder_allRight() {
        Cart cart = anyFilledCart();
        Order order = orderService.getOrder(cart);

        assertNull(order.getId());
        assertEquals(cart.getItems(), order.getItems());
        assertSame(cart.getTotalCost(), order.getSubtotal());
        assertEquals(cart.getTotalQuantity(), order.getTotalQuantity());
        assertEquals(order.getDeliveryCost(), BigDecimal.valueOf(5));
        assertNull(order.getDeliveryAddress());
    }

    @Test
    public void getOrder_emptyCart() {
        Cart cart = new Cart();
        Order order = orderService.getOrder(cart);

        assertNull(order.getId());
        assertEquals(cart.getItems(), order.getItems());
        assertSame(cart.getTotalCost(), order.getSubtotal());
        assertEquals(cart.getTotalQuantity(), order.getTotalQuantity());
        assertNull(order.getDeliveryAddress());
    }

    @Test
    public void getPaymentMethods_test() {
        assertEquals(orderService.getPaymentMethods().get(0), PaymentMethod.CASH);
        assertEquals(orderService.getPaymentMethods().get(1), PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void placeOrder_allRight() {
        Cart cart = anyFilledCart();
        Order order = orderService.getOrder(cart);

        orderService.placeOrder(order);
        OrderDao orderDao = ArrayListOrderDao.getInstance();

        assertTrue(order.getId() > 0);
        assertSame(orderDao.getOrder(order.getId()), order);
    }

}