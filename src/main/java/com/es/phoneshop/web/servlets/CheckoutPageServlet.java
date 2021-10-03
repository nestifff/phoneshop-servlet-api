package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.cart.cartService.DefaultCartService;
import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.PaymentMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cart cart = cartService.getCart(request);
        request.setAttribute("order", orderService.getOrder(cart));
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cart cart = cartService.getCart(request);
        Order order = orderService.getOrder(cart);

        request.setAttribute("order", order);
        request.setAttribute("paymentMethods", orderService.getPaymentMethods());

        Map<String, String> errors = new HashMap<>();

        setRequiredParameter(request, "firstName", errors, order::setFirstName, getPredicateCheckStringPresent());
        setRequiredParameter(request, "lastName", errors, order::setLastName, getPredicateCheckStringPresent());
        setRequiredParameter(request, "phone", errors, order::setPhone, value -> value != null && value.length() > 0 && value.matches("\\+?\\d{7,10}"));
        setRequiredParameter(request, "deliveryAddress", errors, order::setDeliveryAddress, getPredicateCheckStringPresent());

        try {
            setRequiredParameter(request, "deliveryDate", errors,
                    value -> order.setDeliveryDate(LocalDate.parse(value)), it -> true);
        } catch (Exception ex) {
            errors.put("deliveryDate", "Please write in right format");
        }

        setPaymentMethod(request, errors, order);

        if (errors.isEmpty()) {
            orderService.placeOrder(order);
            cartService.clearCart(request, cart);

            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());

        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("order", order);

            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
        }
    }

    private void setRequiredParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                      Consumer<String> consumer, Predicate<String> predicate) {

        String value = request.getParameter(parameter);
        if (!predicate.test(value)) {
            errors.put(parameter, "Value must exist and be in the correct format");
        } else {
            consumer.accept(value);
        }
    }

    private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
        String value = request.getParameter("paymentMethod");
        if (value == null || value.length() <= 0) {
            errors.put("paymentMethod", "Value must exist");
        } else {
            order.setPaymentMethod(PaymentMethod.valueOf(value));
        }
    }

    private Predicate<String> getPredicateCheckStringPresent() {
        return value -> value != null && value.length() > 0;
    }
}
