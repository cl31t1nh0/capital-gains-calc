package org.example.util;

import org.example.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class InputParserTest {

    @Test
    void parsesValidJsonArrayToListOfOperations() {
        String json = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":5000}]";
        List<Operation> operations = InputParser.parseOperations(json);
        Assertions.assertEquals(2, operations.size());
        Assertions.assertEquals("buy", operations.get(0).getOperation());
        Assertions.assertEquals(10.00, operations.get(0).getUnitCost());
        Assertions.assertEquals(10000, operations.get(0).getQuantity());
        Assertions.assertEquals("sell", operations.get(1).getOperation());
        Assertions.assertEquals(20.00, operations.get(1).getUnitCost());
        Assertions.assertEquals(5000, operations.get(1).getQuantity());
    }

    @Test
    void returnsEmptyListForEmptyJsonArray() {
        String json = "[]";
        List<Operation> operations = InputParser.parseOperations(json);
        Assertions.assertTrue(operations.isEmpty());
    }

    @Test
    void throwsExceptionForMalformedJson() {
        String json = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}";
        Assertions.assertThrows(RuntimeException.class, () -> InputParser.parseOperations(json));
    }

    @Test
    void throwsExceptionForNullInput() {
        Assertions.assertThrows(RuntimeException.class, () -> InputParser.parseOperations(null));
    }

    @Test
    void throwsExceptionForNonArrayJson() {
        String json = "{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}";
        Assertions.assertThrows(RuntimeException.class, () -> InputParser.parseOperations(json));
    }
}