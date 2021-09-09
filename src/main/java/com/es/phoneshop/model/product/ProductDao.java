package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    boolean save(Product product);
    boolean delete(Long id);
}
