package com.vinhdd.sbom.api.util.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryResultMapper {
    private final ObjectMapper objectMapper;

    public <T> T mapResult(Map<String, Object> result, Class<T> type) {
        try {
            // Tạo một instance của DTO
            T instance = type.getDeclaredConstructor().newInstance();

            // Lấy danh sách các field của DTO
            for (Field field : type.getDeclaredFields()) {
                String columnName = field.getName();
                Object columnValue = result.get(columnName);
                if (field.isAnnotationPresent(Col.class)) {
                    // Lấy giá trị từ annotation @Col
                    Col colAnnotation = field.getAnnotation(Col.class);
                    columnName = colAnnotation.value();
                    columnValue = result.get(columnName);
                    boolean isJsonString = colAnnotation.jsonString();
                    if (isJsonString) {
                        columnValue = objectMapper.readValue((String) result.get(columnName), field.getType());
                    }
                    boolean isTimestamp = colAnnotation.isTimestamp();
                    if (isTimestamp) {
                        columnValue = ((java.sql.Timestamp) result.get(columnName)).toLocalDateTime();
                        columnValue = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((java.time.LocalDateTime) columnValue);
                    }
                }
                // Lấy giá trị từ Map và gán vào field
                if (result.containsKey(columnName)) {
                    field.setAccessible(true);
                    field.set(instance, columnValue);
                }
            }

            return instance;
        } catch (Exception e) {
            log.error("Failed to map query result to DTO", e);
            throw new RuntimeException("Failed to map query result to DTO", e);
        }
    }
}

