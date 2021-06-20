package ru.javaops.bootjava.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;

@UtilityClass
public class JsonUtil {
    private static ObjectMapper objectMapper;

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) throws IOException {
        ObjectReader reader = objectMapper.readerFor(clazz);
        return reader.<T>readValues(json).readAll();
    }

    public static <T> List<T> readValuesFromHal(String json, Class<T> clazz, String nodeName) throws IOException {
        ObjectReader reader = objectMapper.readerFor(clazz);
        JsonNode usersNode = objectMapper.readTree(json).get("_embedded").get(nodeName);
        return reader.<T>readValues(usersNode.toString()).readAll();
    }

    public static <T> T readValue(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> String writeValue(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}