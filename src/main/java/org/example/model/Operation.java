package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Operation {

    @JsonProperty("operation")
    private String operation;
    @JsonProperty("unit-cost")
    private BigDecimal unitCost;
    @JsonProperty("quantity")
    private int quantity;
}
