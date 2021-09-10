package com.es.phoneshop.model.productDao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productSortEnums.SortField;
import com.es.phoneshop.model.productSortEnums.SortOrder;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    boolean save(Product product);
    boolean delete(Long id);
}
