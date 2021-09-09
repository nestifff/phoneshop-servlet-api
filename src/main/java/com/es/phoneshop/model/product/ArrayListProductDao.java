package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.es.phoneshop.model.product.ProductDaoUtils.*;

public class ArrayListProductDao implements ProductDao {

    private long maxId;
    private List<Product> products;
    ReadWriteLock lock;

    public ArrayListProductDao() {
        this.products = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
        saveSampleProducts();
    }

    @Override
    public Product getProduct(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(it -> id.equals(it.getId()))
                    .findAny()
                    .orElse(null);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {

        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(it -> it.getPrice() != null)
                    .filter(it -> it.getStock() > 0)
                    .filter(it -> query == null || query.isEmpty() ||
                            areStringsContainsCommonWords(query, it.getDescription()))
                    .sorted((it1, it2) ->
                            compareByMatchingWordsNum(
                                    it1.getDescription(),
                                    it2.getDescription(),
                                    query
                            )
                    )
                    .sorted((it1, it2) -> compareForSortingFieldOrder(it1, it2, sortField, sortOrder))
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean save(Product product) {

        lock.writeLock().lock();
        try {

            if (product.getId() == null) {
                addProduct(product);
                return true;

            } else {

                Product existedProductWithThisId = getProduct(product.getId());

                if (existedProductWithThisId == null) {
                    addProduct(product);
                    return true;

                } else {
                    changeExistingProduct(existedProductWithThisId, product);
                    return false;
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id can't be null");
        }

        for (Product product : products) {

            if (product.getId().equals(id)) {

                lock.writeLock().lock();
                try {
                    products.remove(product);
                    return true;
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }

        return false;
    }

    private void saveSampleProducts() {

        lock.writeLock().lock();

        try {

            Currency usd = Currency.getInstance("USD");
            save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
            save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
            save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
            save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
            save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
            save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
            save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
            save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
            save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
            save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
            save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
            save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
            save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

        } finally {
            lock.writeLock().unlock();
        }
    }

    private void addProduct(Product product) {
        product.setId(++maxId);
        products.add(product);
    }

    private void changeExistingProduct(Product existingProduct, Product newProduct) {

        existingProduct.setCode(newProduct.getCode());
        existingProduct.setCurrency(newProduct.getCurrency());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setImageUrl(newProduct.getImageUrl());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setStock(newProduct.getStock());
    }

}
