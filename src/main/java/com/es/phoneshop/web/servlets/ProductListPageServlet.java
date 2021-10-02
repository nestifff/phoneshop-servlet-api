package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.cart.cartService.DefaultCartService;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import com.es.phoneshop.model.product.productSortEnums.SortField;
import com.es.phoneshop.model.product.productSortEnums.SortOrder;
import com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedProducts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import static com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedProducts.createRVProductsForSession;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RecentlyViewedProducts recentlyViewedProducts = createRVProductsForSession(request.getSession());
        request.setAttribute("recentlyViewed", recentlyViewedProducts.getRecentlyViewed());

        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");

        request.setAttribute("products", productDao.findProducts(
                query,
                sortField == null ? null : SortField.valueOf(sortField.toUpperCase()),
                sortOrder == null ? null : SortOrder.valueOf(sortOrder.toUpperCase())
        ));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productIdStr = "";
        try {

            productIdStr = request.getParameter("addedId");
            long productId = Long.parseLong(productIdStr);

            String quantityStr = request.getParameter("quantity");
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            int quantity = format.parse(quantityStr).intValue();

            cartService.add(cartService.getCart(request), productId, quantity);

        } catch (ParseException | IllegalArgumentException | NullPointerException ex) {

            request.setAttribute("error", ex.getMessage());
            request.setAttribute("addedId", productIdStr);

            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products?message=Product added to cart");
    }
}
