package com.sparta.spring_deeping_week_assignment.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {
        CustomException restApiException = new CustomException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(ex.getMessage());

        return new ResponseEntity(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleNotBlankException(ConstraintViolationException ex) {
        CustomException notBlankException = new CustomException();
        notBlankException.setHttpStatus(HttpStatus.BAD_REQUEST);
        notBlankException.setErrorMessage("값은 공백일 수 없습니다.");

        return new ResponseEntity(
                notBlankException,
                HttpStatus.BAD_REQUEST
        );
    }






}
