package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;

class MainIntegrationTest {

    @Test
    @DisplayName("Prints tax results for Caso1")
    void printsTaxResultsForCaso1() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":100}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":50}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso2")
    void printsTaxResultsForCaso2() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\":5000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=10000.00), TaxResult(tax=0.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso3")
    void printsTaxResultsForCaso3() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":5.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":3000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=1000.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso4")
    void printsTaxResultsForCaso4() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":10000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso5")
    void printsTaxResultsForCaso5() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"buy\", \"unit-cost\":25.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":5000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=10000.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso6")
    void printsTaxResultsForCaso6() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":1000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=3000.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso7")
    void printsTaxResultsForCaso7() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":2.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}, {\"operation\":\"sell\", \"unit-cost\":20.00, \"quantity\":2000}, {\"operation\":\"sell\", \"unit-cost\":25.00, \"quantity\":1000}, {\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":15.00, \"quantity\":5000}, {\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\":4350}, {\"operation\":\"sell\", \"unit-cost\":30.00, \"quantity\":650}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=3000.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=3700.00), TaxResult(tax=0.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso8")
    void printsTaxResultsForCaso8() throws Exception {
        String input = "[{\"operation\":\"buy\", \"unit-cost\":10.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\":10000}, {\"operation\":\"buy\", \"unit-cost\":20.00, \"quantity\":10000}, {\"operation\":\"sell\", \"unit-cost\":50.00, \"quantity\":10000}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=80000.00), TaxResult(tax=0.00), TaxResult(tax=60000.00)]\n");
    }

    @Test
    @DisplayName("Prints tax results for Caso9")
    void printsTaxResultsForCaso9() throws Exception {
        String input = "[{\"operation\": \"buy\", \"unit-cost\": 5000.00, \"quantity\": 10}, {\"operation\": \"sell\", \"unit-cost\": 4000.00, \"quantity\": 5}, {\"operation\": \"buy\", \"unit-cost\": 15000.00, \"quantity\": 5}, {\"operation\": \"buy\", \"unit-cost\": 4000.00, \"quantity\": 2}, {\"operation\": \"buy\", \"unit-cost\": 23000.00, \"quantity\": 2}, {\"operation\": \"sell\", \"unit-cost\": 20000.00, \"quantity\": 1}, {\"operation\": \"sell\", \"unit-cost\": 12000.00, \"quantity\": 10}, {\"operation\": \"sell\", \"unit-cost\": 15000.00, \"quantity\": 3}]";
        String output = tapSystemOut(() ->
                withTextFromSystemIn(input).execute(() -> Main.main(new String[]{}))
        );
        assert output.contains("[TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=0.00), TaxResult(tax=1000.00), TaxResult(tax=2400.00)]\n");
    }
}
