package com.mstennie.rezdy.interview.mylunch.util.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple wrapper to ObjectMapper to provide cleaner caller without weird exception handling.
 */
public class JsonMapper {

    public static <T> T[] asArray(JsonNode jsonNode, Class<T[]> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.treeToValue(jsonNode, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
