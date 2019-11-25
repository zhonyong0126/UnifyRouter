package com.feture.learnfilter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error("objectToString Exception ", jsonProcessingException);
        }

        return "";

    }

    public static <T> T stringToObject(String json, Class<T> object) {
        try {
            return objectMapper.readValue(json, object);
        } catch (IOException exception) {
            logger.error("stringToObject Exception ", exception);
        }

        return null;
    }
}
