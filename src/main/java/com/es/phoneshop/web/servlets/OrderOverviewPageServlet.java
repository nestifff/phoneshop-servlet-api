package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String secureOrderId = request.getPathInfo().substring(1);
            Order order = orderDao.getOrderBySecureId(secureOrderId);

            if (order == null) {
                throw new RuntimeException("Order not found");
            }

            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);

        } catch (Exception ex) {
            request.getRequestDispatcher("/WEB-INF/pages/errorOrderNotFound.jsp").forward(request, response);
        }
    }
}
