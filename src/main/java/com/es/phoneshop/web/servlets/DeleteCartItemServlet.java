package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.cart.cartService.DefaultCartService;
import com.es.phoneshop.model.cart.domain.Cart;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            Long productId = Long.valueOf(request.getPathInfo().substring(1));
            Cart cart = cartService.getCart(request);

            if (cartService.delete(cart, productId)) {
                response.sendRedirect(request.getContextPath() + "/cart?message=Cart item deleted successfully");
            } else {
                throw new RuntimeException("");
            }

        } catch (Exception ex) {
            String idStr = request.getPathInfo().substring(1).length() > 0 ? request.getPathInfo().substring(1) : "empty";
            request.setAttribute("productId", idStr);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }


    }
}
