package com.example.testtoy.global;

import com.example.testtoy.global.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        System.out.println("CustomException : {}" + e);

        ErrorResponse response = ErrorResponse.of(e.getErrorCode());

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(RuntimeException e, WebRequest request){
        System.out.println("Conflict : {}" + e.getMessage());

        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
