package com.srllc.shopping_system_bootcamp2025.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus httpStatus;
    private boolean success;
    private String message;
    private T payload;
    private List<T> errors;
    private int errorCode;
    private LocalDateTime timestamp;
    private String path;

}
