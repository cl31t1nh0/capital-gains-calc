package org.example;

import org.example.model.Operation;
import org.example.model.TaxResult;
import org.example.service.TaxCalculatorService;
import org.example.util.InputParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                List<Operation> operations = InputParser.parseOperations(line);
                List<TaxResult> taxResults = TaxCalculatorService.processOperations(operations);
                System.out.println(taxResults);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar entrada: " + e.getMessage());
            e.printStackTrace();
        }
    }
}