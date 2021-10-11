package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.model.dto.BaseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ServiceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers,HttpStatus status,WebRequest request) {
        ApiError apiError = new ApiError("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(javax.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        ApiError apiError= new ApiError("not_found_url",ex);
        return buildResponseEntity(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handleEntityForbidden( ForbiddenException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return buildResponseEntity(apiError,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizeException.class)
    protected ResponseEntity<Object> handleEntityAuthorize(AuthorizeException ex) {
        ApiError apiError = new ApiError(ex);
        return buildResponseEntity(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<Object> handleEntityDuplicated(DuplicateException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError=new ApiError(error,ex);
        return buildResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        logger.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";//"required_parameters";
        ApiError apiError = new ApiError(error, ex);
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<?> methodArgumentTypeMismatchExceptionExceptions(MethodArgumentTypeMismatchException ex) {
        String error = "invalid_parameter";
        ApiError apiError = new ApiError(error, ex);
        return buildResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        ApiError apiError = new ApiError(error,ex);
        return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            String error = "Database error - duplicate_unique_key";
            ApiError apiError = new ApiError(error,ex.getCause());
            return buildResponseEntity(apiError, HttpStatus.CONFLICT);
        }
        ApiError apiError = new ApiError(ex);
        return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<?> handleIllegalArgumentExceptions(IllegalArgumentException ex, HttpServletRequest request) {
        String error = "unexpected_error";
        ApiError apiError = new ApiError(error,ex);
        return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<?> handleAllExceptions(RuntimeException ex, HttpServletRequest request) {
        String error = "unexpected_error";
        ApiError apiError = new ApiError(error,ex);
        return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<?> handleIllegalStateExceptions(IllegalStateException ex) {
        String error = "request_timeout";
        ApiError apiError = new ApiError(error,ex);
        return buildResponseEntity(apiError, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(Error.class)
    public final ResponseEntity<?> handleAllExceptions(Error ex) {
        String error = "unexpected_error";
        ApiError apiError = new ApiError(error,ex);
        return buildResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) throws Exception {
        BaseDTO baseDTO = new ObjectMapper().readValue(ex.getStatusText(), BaseDTO.class);
        return new ResponseEntity<>(baseDTO, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex) throws Exception {
        BaseDTO baseDTO = new ObjectMapper().readValue((((HttpServerErrorException) ex).getResponseBodyAsString()), BaseDTO.class);
        return new ResponseEntity<>(baseDTO, ((HttpServerErrorException) ex).getStatusCode());
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<?> handleServiceException(ServiceException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),ex);
        return buildResponseEntity(apiError, ex.getHttpStatus());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,HttpHeaders headers,HttpStatus status,WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiError apiError=new ApiError(builder.substring(0, builder.length() - 2),ex);
        return buildResponseEntity(apiError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError,HttpStatus httpStatus) {
        BaseDTO<Object> baseDTO =BaseDTO.builder()
                .data(apiError)
                .status(httpStatus)
                .build();
        return new ResponseEntity<>(baseDTO,httpStatus);
    }
}
