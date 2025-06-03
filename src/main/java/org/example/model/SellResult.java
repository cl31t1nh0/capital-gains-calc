package org.example.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellResult {
    private BigDecimal totalQuantity;
    private BigDecimal accumulatedLoss;
    private BigDecimal tax;

    public SellResult(BigDecimal totalQuantity, BigDecimal accumulatedLoss, BigDecimal tax) {
        this.totalQuantity = totalQuantity;
        this.accumulatedLoss = accumulatedLoss;
        this.tax = tax;
    }
}
