package com.examples.SpringBatchSample.handler.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    private String key;
    private String message;
    private HttpStatus httpStatus;
}

