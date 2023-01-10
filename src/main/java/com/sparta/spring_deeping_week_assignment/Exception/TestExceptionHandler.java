package com.sparta.spring_deeping_week_assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TestExceptionHandler {


        @ExceptionHandler(value = {IllegalArgumentException.class})
        public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
            TestException restApiException = new TestException();
            restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
            restApiException.setErrorMessage(ex.getMessage());

            return new ResponseEntity(
                    restApiException,
                    HttpStatus.BAD_REQUEST
            );


    }
}
