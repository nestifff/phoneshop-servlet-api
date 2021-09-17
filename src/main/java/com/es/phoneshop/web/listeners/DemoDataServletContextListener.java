package com.es.phoneshop.web.listeners;

import com.es.phoneshop.model.product.productDao.ArrayListProductDao;
import com.es.phoneshop.model.product.productDao.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.es.phoneshop.model.product.demoData.DemoDataForProductDaoCreator.fillProductDaoDemoData;

public class DemoDataServletContextListener implements ServletContextListener {

    private final ProductDao productDao;

    public DemoDataServletContextListener() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        boolean needToInsertDemoData =
                Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("needToInsertDemoData"));

        if (needToInsertDemoData) {
            saveSampleProductsInProductDao();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void saveSampleProductsInProductDao() {
        fillProductDaoDemoData(productDao);
    }
}
