package com.meta_forge_platform.shared.infrastructure.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Converter
    public static class MapConverter implements AttributeConverter<Map<String, Object>, String> {

        @Override
        public String convertToDatabaseColumn(Map<String, Object> attribute) {
            if (attribute == null) return null;
            try {
                return MAPPER.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Cannot convert Map to JSON", e);
            }
        }

        @Override
        public Map<String, Object> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isBlank()) return null;
            try {
                return MAPPER.readValue(dbData, new TypeReference<LinkedHashMap<String, Object>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Cannot parse JSON to Map: " + dbData, e);
            }
        }
    }

    @Converter
    public static class ListConverter implements AttributeConverter<List<Object>, String> {

        @Override
        public String convertToDatabaseColumn(List<Object> attribute) {
            if (attribute == null) return null;
            try {
                return MAPPER.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Cannot convert List to JSON", e);
            }
        }

        @Override
        public List<Object> convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isBlank()) return null;
            try {
                return MAPPER.readValue(dbData, new TypeReference<List<Object>>() {});
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Cannot parse JSON to List: " + dbData, e);
            }
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) return null;
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot parse JSON to " + clazz.getSimpleName(), e);
        }
    }

    public static String toJson(Object object) {
        if (object == null) return null;
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert object to JSON", e);
        }
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}