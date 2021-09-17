package com.es.phoneshop.web.servlets;

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

import static com.es.phoneshop.model.product.recentlyViewed.RecentlyViewedProducts.createRVProductsForSession;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
}
