package com.senerk.casestudy.flightplanning.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private static final String DATA_NOT_FOUND = "Data not found";
    private static final String SUCESSFUL = "Process Successfully Completed";

    private T data;
    private String message;
    private LocalDateTime responseTime = LocalDateTime.now();

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> dataNotFound() {
        ApiResponse<T> responseBody = new ApiResponse<>();
        responseBody.setMessage(DATA_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok() {
        ApiResponse<T> responseBody = new ApiResponse<>();
        responseBody.setMessage(SUCESSFUL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        ApiResponse<T> responseBody = new ApiResponse<>(data);
        responseBody.setMessage(SUCESSFUL);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> responseBody = new ApiResponse<>(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        ApiResponse<T> responseBody = new ApiResponse<>();
        responseBody.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }
}
