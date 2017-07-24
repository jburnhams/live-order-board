package com.silverbars;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class OrderBook {

    private final AtomicLong idGenerator = new AtomicLong();

    private Map<Long, Order> repository = new ConcurrentHashMap<>();

    /**
     * Registers the given order in the book. Returns an id to uniquely identify order so supports duplicate identical orders
     *
     * @param order to register
     * @return unique identifer for order
     */
    public long registerOrder(Order order) {
        long id = idGenerator.incrementAndGet();
        repository.put(id, order);
        return id;
    }

    public Order retrieveOrder(long orderId) {
        return repository.get(orderId);
    }

    public boolean cancelOrder(long orderId) {
        return repository.remove(orderId) != null;
    }

    /**
     * Returns a new list containing all orders. Sort order is not defined
     * @return new arraylist of orders
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(repository.values());
    }
}
