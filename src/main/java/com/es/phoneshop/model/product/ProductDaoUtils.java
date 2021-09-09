package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductDaoUtils {

    public static boolean areStringsContainsCommonWords(String str1, String str2) {

        return !Collections.disjoint(
                Arrays.asList(str1.toLowerCase().split(" ")),
                Arrays.asList(str2.toLowerCase().split(" "))
        );
    }

    public static int compareByMatchingWordsNum(String whereSearch1, String whereSearch2, String whatSearch) {

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
}
