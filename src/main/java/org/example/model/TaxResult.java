package org.example.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxResult {

    private BigDecimal tax;

    public TaxResult(BigDecimal tax) {
        this.tax = tax;
    }
}
