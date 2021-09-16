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

        try {

            Long productId = Long.valueOf(request.getPathInfo().substring(1));
            String showPriseHistory = request.getParameter("priceHistory");
            Product product = productDao.getProduct(productId);

            if (product == null) {
                throw new RuntimeException("product not found");
            }

            request.setAttribute("product", product);
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
}
