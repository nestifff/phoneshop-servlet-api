package com.es.phoneshop.model.product.recentlyViewed;

import com.es.phoneshop.model.product.domain.Product;

import javax.servlet.http.HttpSession;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class RecentlyViewedProducts {

    public static final String VIEWED_PRODUCTS_SESSION_ATTRIBUTE = RecentlyViewedProducts.class.getName() + "object for certain session";

    private final Deque<Product> recentlyViewed;

    public static RecentlyViewedProducts createRVProductsForSession(HttpSession session) {

        RecentlyViewedProducts recentlyViewedProducts = (RecentlyViewedProducts) session.getAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE);

        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new RecentlyViewedProducts();
            session.setAttribute(VIEWED_PRODUCTS_SESSION_ATTRIBUTE, recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }

    public RecentlyViewedProducts() {
        recentlyViewed = new LinkedList<>();
    }

    public void addRecentlyViewed(Product product) {

        if (!recentlyViewed.contains(product)) {

            recentlyViewed.addFirst(product);
            if (recentlyViewed.size() > 3) {
                recentlyViewed.removeLast();
            }

        } else {
            recentlyViewed.remove(product);
            recentlyViewed.addFirst(product);
        }
    }

    public List<Product> getRecentlyViewed() {
        return List.copyOf(recentlyViewed);
    }
}
