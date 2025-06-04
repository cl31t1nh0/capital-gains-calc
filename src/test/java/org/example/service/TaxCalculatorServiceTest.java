package org.example.service;

import org.example.model.Operation;
import org.example.model.TaxResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TaxCalculatorServiceTest {

    @Test
    @DisplayName("Returns empty list when no operations are provided")
    void returnsEmptyListForNoOperations() {
        List<TaxResult> results = TaxCalculatorService.processOperations(Collections.emptyList());
        Assertions.assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Returns zero tax for a single buy operation")
    void returnsZeroTaxForSingleBuy() {
        Operation buy = new Operation();
        buy.setOperation("buy");
        buy.setQuantity(10);
        buy.setUnitCost(new BigDecimal("100.00"));
        List<TaxResult> results = TaxCalculatorService.processOperations(Collections.singletonList(buy));
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2), results.get(0).getTax());
    }

    @Test
    @DisplayName("Returns zero tax for sell below threshold")
    void returnsZeroTaxForSellBelowThreshold() {
        Operation buy = new Operation();
        buy.setOperation("buy");
        buy.setQuantity(10);
        buy.setUnitCost(new BigDecimal("100.00"));
        Operation sell = new Operation();
        sell.setQuantity(10);
        sell.setUnitCost(new BigDecimal("150.00"));
        sell.setOperation("sell");
        List<TaxResult> results = TaxCalculatorService.processOperations(Arrays.asList(buy, sell));
        Assertions.assertEquals(2, results.size());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2), results.get(1).getTax());
    }

    @Test
    @DisplayName("Calculates tax for sell above threshold with profit")
    void calculatesTaxForSellAboveThresholdWithProfit() {
        Operation buy = new Operation();
        buy.setQuantity(100);
        buy.setUnitCost(new BigDecimal("100.00"));
        buy.setOperation("buy");
        Operation sell = new Operation();
        sell.setQuantity(100);
        sell.setUnitCost(new BigDecimal("300.00"));
        sell.setOperation("sell");
        List<TaxResult> results = TaxCalculatorService.processOperations(Arrays.asList(buy, sell));
        Assertions.assertEquals(2, results.size());
        // Profit: (300-100)*100 = 20000, Tax: 20000*0.2 = 4000.00
        Assertions.assertEquals(new BigDecimal("4000.00"), results.get(1).getTax());
    }

    @Test
    @DisplayName("Accumulates loss and applies it to future profits")
    void accumulatesLossAndAppliesToFutureProfits() {
        Operation buy = new Operation();
        buy.setQuantity(100);
        buy.setUnitCost(new BigDecimal("100.00"));
        buy.setOperation("buy");
        Operation sellLoss = new Operation(); // Loss: 5000
        sellLoss.setOperation("sell");
        sellLoss.setUnitCost(new BigDecimal("50.00"));
        sellLoss.setQuantity(100);
        Operation buy2 = new Operation();
        buy2.setQuantity(100);
        buy2.setUnitCost(new BigDecimal("100.00"));
        buy2.setOperation("buy");
        Operation sellProfit = new Operation(); // Profit: 20000, minus 5000 loss = 15000, Tax: 3000
        sellProfit.setQuantity(100);
        sellProfit.setUnitCost(new BigDecimal("300.00"));
        sellProfit.setOperation("sell");
        List<TaxResult> results = TaxCalculatorService.processOperations(Arrays.asList(buy, sellLoss, buy2, sellProfit));
        Assertions.assertEquals(4, results.size());
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2), results.get(1).getTax());
        Assertions.assertEquals(new BigDecimal("3000.00"), results.get(3).getTax());
    }

    @Test
    @DisplayName("Handles selling more than owned (negative quantity)")
    void handlesSellingMoreThanOwned() {
        Operation buy = new Operation();
        buy.setQuantity(10);
        buy.setUnitCost(new BigDecimal("100.00"));
        buy.setOperation("buy");
        Operation sell = new Operation();
        sell.setQuantity(20);
        sell.setOperation("sell");
        sell.setUnitCost(new BigDecimal("150.00"));
        List<TaxResult> results = TaxCalculatorService.processOperations(Arrays.asList(buy, sell));
        Assertions.assertEquals(2, results.size());
        // Should still calculate based on available quantity, negative quantity allowed
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2), results.get(1).getTax());
    }

    @Test
    @DisplayName("Handles multiple consecutive buys and sells")
    void handlesMultipleConsecutiveBuysAndSells() {
        Operation buy1 = new Operation();
        buy1.setQuantity(10);
        buy1.setUnitCost(new BigDecimal("100.00"));
        buy1.setOperation("buy");
        Operation buy2 = new Operation();
        buy2.setQuantity(20);
        buy2.setUnitCost(new BigDecimal("200.00"));
        buy2.setOperation("buy");
        Operation sell1 = new Operation();
        sell1.setQuantity(15);
        sell1.setUnitCost(new BigDecimal("250.00"));
        sell1.setOperation("sell");
        Operation sell2 = new Operation();
        sell2.setQuantity(10);
        sell2.setOperation("sell");
        sell2.setUnitCost(new BigDecimal("300.00"));
        List<TaxResult> results = TaxCalculatorService.processOperations(Arrays.asList(buy1, buy2, sell1, sell2));
        Assertions.assertEquals(4, results.size());
        // Check that all results are present and tax is calculated for each sell
        Assertions.assertNotNull(results.get(2).getTax());
        Assertions.assertNotNull(results.get(3).getTax());
    }
}