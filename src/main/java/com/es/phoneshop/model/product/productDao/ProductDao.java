package com.es.phoneshop.model.product.productDao;

import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productSortEnums.SortField;
import com.es.phoneshop.model.product.productSortEnums.SortOrder;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    List<Product> findAdvancedProducts(String query, BigDecimal maxPrice, BigDecimal minPrice, SearchAdvancedOption searchOptions);
    List<SearchAdvancedOption> getAdvancedSearchOptions();
    boolean save(Product product);
    boolean delete(Long id);
}
