package com.silverbars;

import java.util.List;

/**
 * Implementation of a live order board using an order book and price demand factory to create summary information
 */
public class LiveOrderBoard {

    private final OrderBook orderBook;
    private final PriceDemandFactory priceDemandFactory;

    public LiveOrderBoard(OrderBook orderBook, PriceDemandFactory priceDemandFactory) {
        this.orderBook = orderBook;
        this.priceDemandFactory = priceDemandFactory;
    }

    public List<PriceDemand> getBuySummary() {
        return priceDemandFactory.getBuyDemandSummary(orderBook.getAllOrders());
    }

    public List<PriceDemand> getSellSummary() {
        return priceDemandFactory.getSellDemandSummary(orderBook.getAllOrders());
    }
}
