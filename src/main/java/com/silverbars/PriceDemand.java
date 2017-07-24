package com.silverbars;

import java.math.BigDecimal;

public class PriceDemand {

    private final BigDecimal price;
    private BigDecimal totalQuantity;

    public PriceDemand(BigDecimal price) {
        this(price, BigDecimal.ZERO);
    }

    public PriceDemand(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.totalQuantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public PriceDemand addQuantity(BigDecimal quantity) {
        totalQuantity = totalQuantity.add(quantity);
        return this;
    }

    @Override
    public String toString() {
        return totalQuantity.stripTrailingZeros() + " kg for £" + price.stripTrailingZeros();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceDemand that = (PriceDemand) o;

        return price.compareTo(that.price) == 0 && totalQuantity.compareTo(that.totalQuantity) == 0;
    }

    @Override
    public int hashCode() {
        int result = price.hashCode();
        result = 31 * result + totalQuantity.hashCode();
        return result;
    }
}
