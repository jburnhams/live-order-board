package com.silverbars;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderBookTest {

    private static final Order TEST_BUY_ORDER = new Order("d", Order.OrderType.BUY, new BigDecimal("3.5"), new BigDecimal("303"));
    private static final Order TEST_SELL_ORDER = new Order("e", Order.OrderType.SELL, new BigDecimal("4.5"), new BigDecimal("306"));
    private OrderBook testee;

    @Before
    public void setup() {
        testee = new OrderBook();
    }

    @Test
    public void shouldRegisterAndRetrieveOrders() {
        long orderId = testee.registerOrder(TEST_BUY_ORDER);
        assertThat(orderId).isGreaterThan(0L);
        
        Order actual = testee.retrieveOrder(orderId);
        assertThat(actual)
                .isNotNull()
                .isEqualToComparingFieldByField(TEST_BUY_ORDER);

        long orderId2 = testee.registerOrder(TEST_SELL_ORDER);
        assertThat(orderId2).isGreaterThan(orderId);

        Order actual2 = testee.retrieveOrder(orderId2);
        assertThat(actual2)
                .isNotNull()
                .isEqualToComparingFieldByField(TEST_SELL_ORDER)
                .isNotEqualTo(actual);

    }

    @Test
    public void shouldReturnNullForUnknownOrder() {
        assertThat(testee.retrieveOrder(1)).isNull();
    }

    @Test
    public void shouldCancelOrder() {
        long orderId = testee.registerOrder(TEST_BUY_ORDER);
        assertThat(orderId).isGreaterThan(0L);

        Order actual = testee.retrieveOrder(orderId);
        assertThat(actual).isNotNull();
        
        boolean cancelResult = testee.cancelOrder(orderId);
        assertThat(cancelResult).isTrue();

        Order cancelled = testee.retrieveOrder(orderId);
        assertThat(cancelled).isNull();
    }

    @Test
    public void shouldRetrieveNewListOfAllOrders() {

        long orderId = testee.registerOrder(TEST_BUY_ORDER);
        testee.registerOrder(TEST_SELL_ORDER);

        List<Order> orders = testee.getAllOrders();

        testee.cancelOrder(orderId); //ensure that list of orders is not affected by subsequent cancellations

        assertThat(orders).containsExactlyInAnyOrder(TEST_BUY_ORDER, TEST_SELL_ORDER);
    }

    @Test
    public void shouldFailToCancelUnknownOrder() {
        assertThat(testee.cancelOrder(1)).isFalse();
    }

}