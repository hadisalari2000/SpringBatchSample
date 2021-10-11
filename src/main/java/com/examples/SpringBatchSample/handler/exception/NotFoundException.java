package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.utils.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NotFoundException extends RuntimeException{

    public NotFoundException(){ }

    private NotFoundException(String message){super(message);}

    public static NotFoundException getInstance(Class clazz, String... searchParamsMap) {
        return new NotFoundException(generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return String.format(
                ApplicationProperties.getProperty("not-found"),
                ApplicationProperties.getProperty(entity.toLowerCase()),
                searchParams);
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}

