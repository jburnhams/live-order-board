package com.silverbars;

import java.math.BigDecimal;

/**
 * Immutable data for an order
 */
public class Order {

    public enum OrderType {
        BUY, SELL
    }

    private final String userId; //in reality would be a User object with further fields
    private final OrderType orderType;
    private final BigDecimal quantity;
    private final BigDecimal price;

    public Order(String userId, OrderType orderType, BigDecimal quantity, BigDecimal price) {
        this.userId = userId;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderType=" + orderType +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
