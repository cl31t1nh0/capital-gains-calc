package org.example.service;

import org.example.model.BuyResult;
import org.example.model.Operation;
import org.example.model.SellResult;
import org.example.model.TaxResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class TaxCalculatorService {

    public static List<TaxResult> processOperations(List<Operation> operations) {
        List<TaxResult> results = new ArrayList<>();

        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal averagePrice = BigDecimal.ZERO;
        BigDecimal accumulatedLoss = BigDecimal.ZERO;

        for (Operation op : operations) {
            if ("buy".equals(op.getOperation())) {
                BuyResult buyResult = processBuy(op, totalQuantity, averagePrice);
                totalQuantity = buyResult.getTotalQuantity();
                averagePrice = buyResult.getAveragePrice();
                results.add(new TaxResult(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
            } else if ("sell".equals(op.getOperation())) {
                SellResult sellResult = processSell(op, totalQuantity, averagePrice, accumulatedLoss);
                totalQuantity = sellResult.getTotalQuantity();
                accumulatedLoss = sellResult.getAccumulatedLoss();
                results.add(new TaxResult(sellResult.getTax()));
            }
        }

        return results;
    }

    private static BuyResult processBuy(Operation op, BigDecimal totalQuantity, BigDecimal averagePrice) {
        BigDecimal buyQuantity = BigDecimal.valueOf(op.getQuantity());
        BigDecimal buyCost = op.getUnitCost().multiply(buyQuantity);
        BigDecimal currentTotalCost = averagePrice.multiply(totalQuantity);
        BigDecimal newTotalQuantity = totalQuantity.add(buyQuantity);

        BigDecimal newAveragePrice = averagePrice;
        if (newTotalQuantity.compareTo(BigDecimal.ZERO) > 0) {
            newAveragePrice = currentTotalCost.add(buyCost)
                    .divide(newTotalQuantity, 10, RoundingMode.HALF_UP);
        }

        return new BuyResult(newTotalQuantity, newAveragePrice);
    }

    private static SellResult processSell(Operation op, BigDecimal totalQuantity, BigDecimal averagePrice, BigDecimal accumulatedLoss) {
        BigDecimal quantitySold = BigDecimal.valueOf(op.getQuantity());
        BigDecimal totalSellValue = op.getUnitCost().multiply(quantitySold);
        BigDecimal totalCostBasis = averagePrice.multiply(quantitySold);
        BigDecimal profit = totalSellValue.subtract(totalCostBasis);

        BigDecimal tax = BigDecimal.ZERO;

        if (profit.compareTo(BigDecimal.ZERO) > 0) {
            if (totalSellValue.compareTo(new BigDecimal("20000.00")) > 0) {
                if (accumulatedLoss.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal deductible = profit.min(accumulatedLoss);
                    profit = profit.subtract(deductible);
                    accumulatedLoss = accumulatedLoss.subtract(deductible);
                }
                tax = profit.multiply(new BigDecimal("0.20"));
            }
        } else {
            accumulatedLoss = accumulatedLoss.add(profit.abs());
        }

        BigDecimal newTotalQuantity = totalQuantity.subtract(quantitySold);
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        return new SellResult(newTotalQuantity, accumulatedLoss, tax);
    }
}