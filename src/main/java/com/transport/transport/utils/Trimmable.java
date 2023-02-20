package com.transport.transport.utils;

import com.transport.transport.exception.BadRequestException;

import java.lang.reflect.Field;

public interface Trimmable {
    default void trim() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().contains("Password")) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    if (value instanceof String) {
                        String trimmed = (String) value;
                        field.set(this, trimmed.trim());
                    }
                }
            } catch (Exception e) {
                throw new BadRequestException("Object trim fail!");
            }
        }
    }
}
