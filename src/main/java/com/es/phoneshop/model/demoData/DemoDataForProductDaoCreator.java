package com.es.phoneshop.model.demoData;

import com.es.phoneshop.model.product.PriceHistoryItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productDao.ProductDao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DemoDataForProductDaoCreator {

    public static void fillProductDaoDemoData(ProductDao productDao) {
        Currency usd = Currency.getInstance("USD");

        List<PriceHistoryItem> priceHistory = new ArrayList<>();
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2010, 11, 12), BigDecimal.valueOf(130), usd));

        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 1, 10), BigDecimal.valueOf(200), usd));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 1, 10), BigDecimal.valueOf(200), usd));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 5, 22), BigDecimal.valueOf(1000), usd));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2011, 10, 15), BigDecimal.valueOf(600), usd));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2012, 1, 10), BigDecimal.valueOf(800), usd));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2012, 4, 18), BigDecimal.valueOf(200), usd));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2012, 5, 5), BigDecimal.valueOf(1200), usd));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2012, 12, 1), BigDecimal.valueOf(100), usd));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2013, 1, 13), BigDecimal.valueOf(400), usd));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2013, 3, 10), BigDecimal.valueOf(600), usd));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2014, 2, 14), BigDecimal.valueOf(1000), usd));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", priceHistory));
        priceHistory.add(new PriceHistoryItem(LocalDate.of(2015, 7, 15), BigDecimal.valueOf(600), usd));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", priceHistory));
    }
}
