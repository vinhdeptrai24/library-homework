package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.library.dto.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(ex.getCode(),ex.getMessage()));
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException bu){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.of(bu.getCode(), bu.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException va){
        Map<String,String> fields= new LinkedHashMap<>();
        va.getBindingResult().getFieldErrors()
                .forEach(error -> fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
        ErrorResponse body = new ErrorResponse("VALIDATION_ERROR", "Dữ liệu không hợp lệ", fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleReadable(HttpMessageNotReadableException re){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("INVALID_REQUEST_BODY", "Nội dung request không đọc được"));
    }
}
