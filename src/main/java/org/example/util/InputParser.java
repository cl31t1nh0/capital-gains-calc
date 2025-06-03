package org.example.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Operation;

import java.util.List;

public class InputParser {
    public static List<Operation> parseOperations(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<Operation>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para lista de Operation", e);
        }
    }

}
