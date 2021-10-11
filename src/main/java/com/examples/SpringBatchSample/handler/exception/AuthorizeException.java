package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.utils.ApplicationProperties;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AuthorizeException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public AuthorizeException() {
        super();
    }

    private AuthorizeException(String message, HttpStatus status) {
        super(message);
        this.status=status;
    }

    public static AuthorizeException getInstance(String key){
        return new AuthorizeException(ApplicationProperties.getProperty(key), HttpStatus.UNAUTHORIZED);
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
