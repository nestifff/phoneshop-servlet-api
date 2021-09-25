package com.es.phoneshop.model.product.productDao;

import com.es.phoneshop.model.product.domain.Product;
import com.es.phoneshop.model.product.productSortEnums.SortField;
import com.es.phoneshop.model.product.productSortEnums.SortOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDaoFindProductsUtils {

    public static boolean needToAddThisProduct(String query, Product product) {

        return query == null ||
                query.isEmpty() ||
                areStringsContainsCommonWords(query, product.getDescription());
    }


    public static boolean areStringsContainsCommonWords(String query, String whereFind) {

        return !Collections.disjoint(
                Arrays.asList(query.toLowerCase().split(" ")),
                Arrays.asList(whereFind.toLowerCase().split(" "))
        );
    }

    public static int compare(Product product1, Product product2, SortField sortField, SortOrder sortOrder, String query) {

        if (sortField == null || sortOrder == null) {
            return compareByMatchingWordsNum(product1.getDescription(), product2.getDescription(), query);
        } else {
            return compareForSortingFieldOrder(product1, product2, sortField, sortOrder);
        }
    }

    public static int compareByMatchingWordsNum(String whereSearch1, String whereSearch2, String whatSearch) {

        if (whatSearch == null) {
            return 0;
        }

        List<String> whereSearchWords1 = new ArrayList<>(Arrays.asList(whereSearch1.toLowerCase().split(" ")));
        List<String> whereSearchWords2 = new ArrayList<>(Arrays.asList(whereSearch2.toLowerCase().split(" ")));
        List<String> whatSearchWords = new ArrayList<>(Arrays.asList(whatSearch.toLowerCase().split(" ")));

        int numToMatchMax = whatSearchWords.size();

        whereSearchWords1.retainAll(whatSearchWords);
        whereSearchWords2.retainAll(whatSearchWords);

        int matchIn1 = numToMatchMax - whereSearchWords1.size();
        int matchIn2 = numToMatchMax - whereSearchWords2.size();

        return Integer.compare(matchIn1, matchIn2);
    }

    public static int compareForSortingFieldOrder(Product product1, Product product2, SortField sortField, SortOrder sortOrder) {

        if (sortField == null || sortOrder == null) {
            return 0;
        }

        if (sortField == SortField.DESCRIPTION) {

            if (sortOrder == SortOrder.ASC) {
                return product1.getDescription().compareTo(product2.getDescription());
            } else if (sortOrder == SortOrder.DESC) {
                return product2.getDescription().compareTo(product1.getDescription());
            }

        } else if (sortField == SortField.PRICE) {

            if (sortOrder == SortOrder.ASC) {
                return product1.getPrice().compareTo(product2.getPrice());
            } else if (sortOrder == SortOrder.DESC) {
                return product2.getPrice().compareTo(product1.getPrice());
            }
        }

        return 0;
    }
}
