package com.examples.SpringBatchSample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO<T> {

    private T data;

    @Builder.Default
    private HttpStatus status=HttpStatus.OK;
}
