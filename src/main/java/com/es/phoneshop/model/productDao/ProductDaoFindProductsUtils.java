package com.es.phoneshop.model.productDao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.productSortEnums.SortField;
import com.es.phoneshop.model.productSortEnums.SortOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDaoFindProductsUtils {

    public static boolean areStringsContainsCommonWords(String query, String whereFind) {

        if (query == null) {
            return true;
        }

        return !Collections.disjoint(
                Arrays.asList(query.toLowerCase().split(" ")),
                Arrays.asList(whereFind.toLowerCase().split(" "))
        );
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

        if (sortField == SortField.description) {

            if (sortOrder == SortOrder.asc) {
                return product1.getDescription().compareTo(product2.getDescription());
            } else if (sortOrder == SortOrder.desc) {
                return product2.getDescription().compareTo(product1.getDescription());
            }

        } else if (sortField == SortField.price) {

            if (sortOrder == SortOrder.asc) {
                return product1.getPrice().compareTo(product2.getPrice());
            } else if (sortOrder == SortOrder.desc) {
                return product2.getPrice().compareTo(product1.getPrice());
            }
        }

        return 0;
    }
}
