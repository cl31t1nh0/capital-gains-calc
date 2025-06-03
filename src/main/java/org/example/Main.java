package org.example;

import org.example.model.Operation;
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
                // Processar operações aqui
                System.out.println(operations);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar entrada: " + e.getMessage());
            e.printStackTrace();
        }
    }
}