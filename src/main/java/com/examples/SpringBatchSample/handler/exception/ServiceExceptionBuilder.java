package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.utils.ApplicationProperties;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;

public class ServiceExceptionBuilder {
    @Synchronized
    public static ServiceException serviceExceptionBuilder(String key, HttpStatus httpStatus) {
        return ServiceException.builder()
                .key(key)
                .message(ApplicationProperties.getProperty(key))
                .httpStatus(httpStatus)
                .build();
    }
}
