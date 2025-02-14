package org.prd.authserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static <T> T getClassFromJson(Object res, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(res);
            return objectMapper.readValue(responseBody, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T getClassFromBytes(byte[] bytes, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(bytes, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}