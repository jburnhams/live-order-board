package com.silverbars;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Creates buy and sell summary price demand for a given list of orders
 */
public class PriceDemandFactory {

    private List<PriceDemand> getFilteredSortedPriceDemand(List<Order> orders, Order.OrderType orderType, boolean ascending) {
        PriceDemandOrderedCollector collector = new PriceDemandOrderedCollector();
        orders.stream()
                .filter(order -> order.getOrderType() == orderType)
                .sorted(Comparator.comparing(Order::getPrice, ascending ? Comparator.naturalOrder() : Comparator.reverseOrder()))
                .forEach(collector::add);
        return collector.result;
    }

    public List<PriceDemand> getBuyDemandSummary(List<Order> orders) {
        return getFilteredSortedPriceDemand(orders, Order.OrderType.BUY, false);
    }

    public List<PriceDemand> getSellDemandSummary(List<Order> orders) {
        return getFilteredSortedPriceDemand(orders, Order.OrderType.SELL, true);
    }

    static class PriceDemandOrderedCollector {
        private final List<PriceDemand> result = new ArrayList<>();
        private PriceDemand current = null;

        /**
         * All demand from order, must be called on orders sorted by price
         * @param o input order sorted by price
         */
        void add(Order o) {
            if (current == null || current.getPrice().compareTo(o.getPrice()) != 0) {
                current = new PriceDemand(o.getPrice());
                result.add(current);
            }
            current.addQuantity(o.getQuantity());
        }
    }
}
