package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cartService.CartService;
import com.es.phoneshop.model.cartService.DefaultCartService;
import com.es.phoneshop.model.exceptions.ProductNotFoundInDaoException;
import com.es.phoneshop.model.exceptions.ProductStockLessThenRequiredException;
import com.es.phoneshop.model.exceptions.QuantityLessThenZeroException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productDao.ArrayListProductDao;
import com.es.phoneshop.model.productDao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Long productId = parseProductId(request);
            String showPriseHistory = request.getParameter("priceHistory");
            Product product = productDao.getProduct(productId);

            if (product == null) {
                throw new RuntimeException("product not found");
            }

            request.setAttribute("product", product);
            request.setAttribute("cart", cartService.getCart(request));

            if (showPriseHistory == null) {
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
            }

        } catch (Exception ex) {

            String idStr = request.getPathInfo();
            if (idStr == null || idStr.length() <= 1) {
                idStr = "empty";
                request.setAttribute("productId", idStr);
            } else {
                request.setAttribute("productId", idStr.substring(1));
            }
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int quantity;
        Long productId;

        try {
            String quantityStr = request.getParameter("quantity");
            productId = parseProductId(request);

            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(quantityStr).intValue();

        } catch (Exception ex) {
            request.setAttribute("error", "Not not a number");
            doGet(request, response);
            return;
        }

        try {
            cartService.add(cartService.getCart(request), productId, quantity);

        } catch (ProductNotFoundInDaoException |
                ProductStockLessThenRequiredException |
                QuantityLessThenZeroException ex
        ) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Product added to cart");
    }

    private Long parseProductId(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
