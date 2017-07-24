package com.silverbars;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceDemandFactoryTest {

    private static final Order BUY_ORDER_1 = new Order("d", Order.OrderType.BUY, new BigDecimal("3.5"), new BigDecimal("303"));
    private static final Order BUY_ORDER_2 = new Order("d", Order.OrderType.BUY, new BigDecimal("5.5"), new BigDecimal("310"));
    private static final Order BUY_ORDER_3 = new Order("c", Order.OrderType.BUY, new BigDecimal("6.50"), new BigDecimal("310.0"));
    private static final PriceDemand BUY_DEMAND_1 = new PriceDemand(new BigDecimal("303"), new BigDecimal("3.5"));
    private static final PriceDemand BUY_DEMAND_2 = new PriceDemand(new BigDecimal("310"), new BigDecimal("5.5"));
    private static final PriceDemand BUY_DEMAND_2_3 = new PriceDemand(new BigDecimal("310"), new BigDecimal("12"));


    private static final Order SELL_ORDER_1 = new Order("e", Order.OrderType.SELL, new BigDecimal("4.5"), new BigDecimal("320"));
    private static final Order SELL_ORDER_2 = new Order("e", Order.OrderType.SELL, new BigDecimal("6.5"), new BigDecimal("306"));
    private static final Order SELL_ORDER_3 = new Order("f", Order.OrderType.SELL, new BigDecimal("3.50"), new BigDecimal("306.0"));
    private static final PriceDemand SELL_DEMAND_1 = new PriceDemand(new BigDecimal("320"), new BigDecimal("4.5"));
    private static final PriceDemand SELL_DEMAND_2 = new PriceDemand(new BigDecimal("306"), new BigDecimal("6.5"));
    private static final PriceDemand SELL_DEMAND_2_3 = new PriceDemand(new BigDecimal("306"), new BigDecimal("10"));

    private PriceDemandFactory testee;

    @Before
    public void setup() {
        testee = new PriceDemandFactory();
    }

    @Test
    public void shouldReturnSellDemandInAscendingPriceOrder() {
        List<Order> orders = Arrays.asList(BUY_ORDER_1, SELL_ORDER_1, SELL_ORDER_2);
        assertThat(testee.getSellDemandSummary(orders)).containsExactly(SELL_DEMAND_2, SELL_DEMAND_1);
    }

    @Test
    public void shouldReturnBuyDemandInDescendingPriceOrder() {
        List<Order> orders = Arrays.asList(BUY_ORDER_1, BUY_ORDER_2, SELL_ORDER_1);
        assertThat(testee.getBuyDemandSummary(orders)).containsExactly(BUY_DEMAND_2, BUY_DEMAND_1);
    }

    @Test
    public void shouldMergeBuyDemandForSamePrice() {
        List<Order> orders = Arrays.asList(BUY_ORDER_1, BUY_ORDER_2, BUY_ORDER_3);
        assertThat(testee.getBuyDemandSummary(orders)).containsExactly(BUY_DEMAND_2_3, BUY_DEMAND_1);
    }

    @Test
    public void shouldReturnEmptyListForNoSellDemand() {
        assertThat(testee.getSellDemandSummary(Collections.emptyList())).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListForNoBuyDemand() {
        assertThat(testee.getBuyDemandSummary(Collections.emptyList())).isEmpty();
    }

    @Test
    public void shouldMergeSellDemandForSamePrice() {
        List<Order> orders = Arrays.asList(SELL_ORDER_1, SELL_ORDER_2, SELL_ORDER_3);
        assertThat(testee.getSellDemandSummary(orders)).containsExactly(SELL_DEMAND_2_3, SELL_DEMAND_1);
    }

}
