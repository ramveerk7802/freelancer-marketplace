package com.rvcode.freelancerMarketplace.common.exception;


import com.rvcode.freelancerMarketplace.common.exception.exception_reponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserExistence.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserExistence e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage(),HttpStatus.CONFLICT, LocalDateTime.now()));
    }


    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ErrorResponse> MyCustomNullException(MyCustomException e){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(),HttpStatus.BAD_REQUEST,LocalDateTime.now()));
    }
}
