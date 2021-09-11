package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productDao.ArrayListProductDao;
import com.es.phoneshop.model.productDao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String productIdStr = request.getPathInfo();
        String showPriseHistory = request.getParameter("priceHistory");
        Product product = null;

        if (productIdStr == null || productIdStr.length() <= 1) {
            productIdStr = "empty";
        } else {
            productIdStr = productIdStr.substring(1);
            product = productDao.getProduct(Long.valueOf(productIdStr));
        }

        if (product == null) {
            request.setAttribute("productId", productIdStr);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        } else {
            request.setAttribute("product", product);
            if (showPriseHistory == null) {
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } else if (Boolean.parseBoolean(showPriseHistory)) {
                request.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(request, response);
            }
        }
    }
}
