package org.example.spacecatsmarket.web;

import lombok.extern.slf4j.Slf4j;
import org.example.spacecatsmarket.featuretoggle.exception.FeatureNotAvailableException;
import org.example.spacecatsmarket.service.exception.ProductNotFoundException;
import org.example.spacecatsmarket.web.exception.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class})
    ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {

        var errorDetail = ErrorDetail.builder()
                .error("Product Not Found")
                .status(NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.info("Product Not Found exception raised");
        return ResponseEntity.status(NOT_FOUND).body(errorDetail);
    }

    @ExceptionHandler({FeatureNotAvailableException.class})
    ResponseEntity<Object> handleFeatureNotAvailableException(FeatureNotAvailableException ex, WebRequest request) {
        var errorDetail = ErrorDetail.builder()
                .error("Feature Not Available")
                .status(NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.info("Feature Not Available exception raised");
        return ResponseEntity.status(NOT_FOUND).body(errorDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getFieldErrors();

        List<String> errorMessages = errors.stream()
                .map(err -> String.format("Field '%s': %s", err.getField(), err.getDefaultMessage()))
                .toList();

        String message = String.join(", ", errorMessages);

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(BAD_REQUEST.value())
                .error("Validation Error")
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        log.info("Input params validation failed: {}", message);
        return ResponseEntity.status(BAD_REQUEST).body(errorDetail);
    }



}
