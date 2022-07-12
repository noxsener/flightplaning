package com.senerk.casestudy.flightplanning.controller;

import com.senerk.casestudy.flightplanning.models.ApiResponse;
import com.senerk.casestudy.flightplanning.models.ValidatorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            // String fieldName = ((org.springframework.validation.FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            if (StringUtils.isNotBlank(message)) {
                ApiResponse<Object> responseBody = new ApiResponse<>();
                responseBody.setMessage(message);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiResponse<Object> responseBody = new ApiResponse<>();
        responseBody.setMessage(ex.getMessage());
        if (ex instanceof ValidatorException || ex instanceof PropertyReferenceException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        } else {
            LOG.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
