package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;
import com.es.phoneshop.model.product.productDao.SearchAdvancedOption;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdvancedSearchPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String query = request.getParameter("query");
        Map<String, String> errors = new HashMap<>();

        if (query != null) {

            BigDecimal maxPrice = getMinMaxPriceValue(request, errors, "maxPrice");
            BigDecimal minPrice = getMinMaxPriceValue(request, errors, "minPrice");

            SearchAdvancedOption searchOptions = SearchAdvancedOption.ALL_WORDS;
            try {
                searchOptions = SearchAdvancedOption.valueOf(request.getParameter("searchOption"));
            } catch (Exception ex) {
                errors.put("searchOption", "Invalid search option");
            }

            if (errors.isEmpty()) {
                request.setAttribute(
                        "products",
                        productDao.findAdvancedProducts(query, maxPrice, minPrice, searchOptions)
                );
            } else {
                setEmptyProducts(request);
            }

        } else {
            setEmptyProducts(request);
        }

        request.setAttribute("errors", errors);
        request.setAttribute("searchOptions", productDao.getAdvancedSearchOptions());
        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }

    private BigDecimal getMinMaxPriceValue(HttpServletRequest request, Map<String, String> errors, String parameterName) {

        String priceStr = request.getParameter(parameterName);
        if (priceStr != null && priceStr.length() > 0) {
            try {
                return new BigDecimal(priceStr);
            } catch (Exception ex) {
                errors.put(parameterName, "Invalid number");
            }
        }
        return null;

    }

    private void setEmptyProducts(HttpServletRequest request) {
        request.setAttribute("products", new ArrayList<Product>());
    }

}
