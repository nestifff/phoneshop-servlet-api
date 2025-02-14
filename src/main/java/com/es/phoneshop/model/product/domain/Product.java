package com.es.phoneshop.model.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Product implements Serializable {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    private final List<PriceHistoryItem> priceHistory;

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl, List<PriceHistoryItem> priceHistory) {

        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;

        this.priceHistory = new ArrayList<>();
        this.priceHistory.addAll(priceHistory);
        this.priceHistory.add(
                new PriceHistoryItem(LocalDate.now(), price, currency)
        );
        this.priceHistory.sort(Collections.reverseOrder());
    }

    public void addPriceHistory(PriceHistoryItem item) {
        if (item != null) {
            this.priceHistory.add(item);
            priceHistory.sort(Collections.reverseOrder());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock &&
                Objects.equals(id, product.id) &&
                Objects.equals(code, product.code) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(currency, product.currency) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, price, currency, stock, imageUrl);
    }

    @Override
    public String toString() {
        return description + ", price: " + price.toString();
    }

    public List<PriceHistoryItem> getPriceHistory() {
        return List.copyOf(priceHistory);
    }
}