package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class PriceHistoryItem implements Comparable<PriceHistoryItem> {

    private LocalDate date;
    private BigDecimal price;
    private Currency currency;

    public PriceHistoryItem(LocalDate date, BigDecimal price, Currency currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public int compareTo(PriceHistoryItem o) {
        return this.date.compareTo(o.date);
    }
}
