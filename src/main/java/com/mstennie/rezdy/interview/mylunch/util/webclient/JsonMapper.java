package com.mstennie.rezdy.interview.mylunch.util.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    public static <T> T[] asArray(String jsonValue, Class<T[]> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonValue, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T[] asArray(JsonNode jsonNode, Class<T[]> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(jsonNode, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
