package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.cart.domain.CartItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    private static volatile DefaultOrderService instance;

    private final OrderDao orderDao;

    public static DefaultOrderService getInstance() {

        if (instance == null) {
            synchronized (DefaultOrderService.class) {

                if (instance == null) {
                    instance = new DefaultOrderService();
                }
            }
        }
        return instance;
    }

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();
        setValuesToOrder(order, cart);
        return order;
    }

    private void setValuesToOrder(Order order, Cart cart) {

        order.setItems(cart.getItems().stream().map(it -> {
            try {
                return (CartItem) it.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));

        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost(order.getSubtotal()));
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        order.setTotalQuantity(cart.getTotalQuantity());
    }

    private BigDecimal calculateDeliveryCost(BigDecimal subtotal) {
        if (subtotal.intValue() < 100) {
            return new BigDecimal(20);
        }
        return new BigDecimal(5);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }
}
