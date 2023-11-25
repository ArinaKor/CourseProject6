package com.example.servercurs.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void serialize(List<String> list) throws IOException {
         mapper.writeValueAsString(list);
    }

    public static List<String> deserialize(String json) throws IOException {
        return mapper.readValue(json, List.class);
    }
}
