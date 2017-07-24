package com.silverbars;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveOrderBoardTest {

    @Test
    public void summaryExercise() {
        OrderBook orderBook = new OrderBook();
        LiveOrderBoard liveOrderBoard = new LiveOrderBoard(orderBook, new PriceDemandFactory());

        orderBook.registerOrder(new Order("user1", Order.OrderType.SELL, new BigDecimal(3.5), new BigDecimal(306)));
        orderBook.registerOrder(new Order("user2", Order.OrderType.SELL, new BigDecimal(1.2), new BigDecimal(310)));
        orderBook.registerOrder(new Order("user3", Order.OrderType.SELL, new BigDecimal(1.5), new BigDecimal(307)));
        orderBook.registerOrder(new Order("user4", Order.OrderType.SELL, new BigDecimal(2.0), new BigDecimal(306)));

        assertThat(liveOrderBoard.getSellSummary()).containsExactly(
                new PriceDemand(new BigDecimal(306), new BigDecimal(5.5)),
                new PriceDemand(new BigDecimal(307), new BigDecimal(1.5)),
                new PriceDemand(new BigDecimal(310), new BigDecimal(1.2))
        );

        long orderId = orderBook.registerOrder(new Order("user2", Order.OrderType.BUY, new BigDecimal(2.0), new BigDecimal(305)));
        assertThat(liveOrderBoard.getBuySummary()).containsExactly(
                new PriceDemand(new BigDecimal(305), new BigDecimal(2))
        );

        orderBook.cancelOrder(orderId);
        assertThat(liveOrderBoard.getBuySummary()).isEmpty();
    }

}