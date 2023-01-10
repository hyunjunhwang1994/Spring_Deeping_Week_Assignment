package com.sparta.spring_deeping_week_assignment.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TestException {

    private String errorMessage;
    private HttpStatus httpStatus;

}
