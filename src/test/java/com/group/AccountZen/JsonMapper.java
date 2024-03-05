package com.group.AccountZen;

import com.fasterxml.jackson.databind.ObjectMapper;

class JsonMapper {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}