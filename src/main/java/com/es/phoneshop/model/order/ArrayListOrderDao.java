package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao {

    private static volatile OrderDao instance;

    private long maxId;
    private List<Order> orders;
    ReadWriteLock lock;

    private ArrayListOrderDao() {
        this.orders = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
    }

    public static OrderDao getInstance() {

        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {

                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Order getOrder(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(it -> id.equals(it.getId()))
                    .findAny()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        if (secureId == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(it -> secureId.equals(it.getSecureId()))
                    .findAny()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean save(Order order) {

        lock.writeLock().lock();
        try {

            if (order.getId() == null) {
                addOrder(order);
                return true;

            } else {
                Order existedOrderWithThisId = getOrder(order.getId());
                if (existedOrderWithThisId == null) {
                    addOrder(order);
                    return true;
                } else {
                    changeExistingProduct(existedOrderWithThisId, order);
                    return false;
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
    }


    private void addOrder(Order order) {
        order.setId(++maxId);
        orders.add(order);
    }

    private void changeExistingProduct(Order existingOrder, Order newOrder) {

        existingOrder.setPaymentMethod(newOrder.getPaymentMethod());
        existingOrder.setPhone(newOrder.getPhone());
        existingOrder.setDeliveryDate(newOrder.getDeliveryDate());
        existingOrder.setFirstName(newOrder.getFirstName());
        existingOrder.setTotalQuantity(newOrder.getTotalQuantity());
        existingOrder.setTotalCost(newOrder.getTotalCost());
        existingOrder.setDeliveryCost(newOrder.getDeliveryCost());
        existingOrder.setSubtotal(newOrder.getSubtotal());
        existingOrder.setItems(newOrder.getItems());
        existingOrder.setDeliveryAddress(newOrder.getDeliveryAddress());
        existingOrder.setId(newOrder.getId());
        existingOrder.setLastName(newOrder.getLastName());
    }
}
