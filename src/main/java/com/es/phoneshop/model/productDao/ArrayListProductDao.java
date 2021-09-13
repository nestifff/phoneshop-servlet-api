package com.es.phoneshop.model.productDao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productSortEnums.SortField;
import com.es.phoneshop.model.productSortEnums.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.es.phoneshop.model.productDao.ProductDaoFindProductsUtils.*;

public class ArrayListProductDao implements ProductDao {

    private static volatile ProductDao instance;

    private long maxId;
    private List<Product> products;
    ReadWriteLock lock;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
    }

    public static synchronized ProductDao getInstance() {

        if (instance == null) {
            synchronized (ArrayListProductDao.class) {

                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
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
                    .filter(it -> it.getPrice() != null && it.getStock() > 0)
                    .filter(it -> needToAddThisProduct(query, it))
                    .sorted((it1, it2) -> compare(it1, it2, sortField, sortOrder, query))
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
