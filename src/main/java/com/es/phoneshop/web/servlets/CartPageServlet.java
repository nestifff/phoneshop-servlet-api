package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.cart.cartService.DefaultCartService;
import com.es.phoneshop.model.cart.domain.Cart;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedProducts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedProducts.createRVProductsForSession;

public class CartPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RecentlyViewedProducts recentlyViewedProducts = createRVProductsForSession(request.getSession());
        request.setAttribute("recentlyViewed", recentlyViewedProducts.getRecentlyViewed());

        Cart cart = cartService.getCart(request);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
