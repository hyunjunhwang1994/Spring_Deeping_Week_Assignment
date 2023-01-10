package com.sparta.spring_deeping_week_assignment.dto;


import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class UserSignupRequestDto {

    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9]).{4,10}$",
    message = ResponseMessage.CREATED_USER_FAIL_USERNAME_FAIL)
    private String username;


    @Pattern(regexp = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])^.{8,15}$",
    message = ResponseMessage.CREATED_USER_FAIL_PASSWORD_FAIL)
    private String password;


    private boolean admin = false;
    private String adminToken = "";




}
