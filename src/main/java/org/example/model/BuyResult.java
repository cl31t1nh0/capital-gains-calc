package org.example.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuyResult {
    private final BigDecimal totalQuantity;
    private final BigDecimal averagePrice;

    public BuyResult(BigDecimal totalQuantity, BigDecimal averagePrice) {
        this.totalQuantity = totalQuantity;
        this.averagePrice = averagePrice;
    }
}
