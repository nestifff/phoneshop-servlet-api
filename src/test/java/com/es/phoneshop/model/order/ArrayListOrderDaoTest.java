package com.es.phoneshop.model.order;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {

    private OrderDao orderDao;

    @Before
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Test
    public void getInstance_returnSame() {
        assertSame(orderDao, ArrayListOrderDao.getInstance());
    }

    @Test
    public void save_allRight() {
        Order order = new Order();
        orderDao.save(order);
        assertEquals(1L, (long) order.getId());
    }

    @Test
    public void save_haveId() {
        Order order = new Order();
        order.setId(1000L);
        orderDao.save(order);
        assertFalse(orderDao.save(order));
        assertTrue(order.getId() > 0);
    }

    @Test
    public void save_alreadyExist() {
        Order order1 = new Order();
        Order order2 = new Order();
        order2.setId(1L);
        order2.setLastName("sagdag");
        orderDao.save(order1);
        order2.setId(order1.getId());
        boolean isAdded = orderDao.save(order2);
        assertFalse(isAdded);
        assertEquals(order1.getLastName(), order2.getLastName());
    }

    @Test
    public void get_allRight() {
        Order order = new Order();
        orderDao.save(order);
        assertSame(order, orderDao.getOrder(order.getId()));
    }

    @Test
    public void get_notExist() {
        assertSame(null, orderDao.getOrder(10000L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void get_nullId() {
        orderDao.getOrder(null);
    }

    /*@Test
    public void save_allRight() {
        Order order = new Order();
        orderDao.save(order);
        assertEquals(1L, (long) order.getId());
    }*/

}