package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.utils.ApplicationProperties;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ForbiddenException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public ForbiddenException() {
        super();
    }

    private ForbiddenException(String message, HttpStatus status) {
        super(message);
        this.status=status;
    }

    public static ForbiddenException getInstance(Class clazz){
        String entity=clazz.getSimpleName();
        String message=String.format(
                ApplicationProperties.getProperty("forbidden"),
                ApplicationProperties.getProperty(entity.toLowerCase()));
        return new ForbiddenException(message, HttpStatus.FORBIDDEN);
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
