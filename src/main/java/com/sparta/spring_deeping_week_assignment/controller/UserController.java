package com.sparta.spring_deeping_week_assignment.controller;


import com.sparta.spring_deeping_week_assignment.dto.UserLoginRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.UserLoginResponseDto;
import com.sparta.spring_deeping_week_assignment.dto.UserSignupRequestDto;
import com.sparta.spring_deeping_week_assignment.dto.UserSignupResponseDto;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @ApiIgnore
    @GetMapping("/login-page")
    public UserSignupResponseDto  loginPage() {



        //AOP 버전
        throw new IllegalArgumentException(ResponseMessage.TOKEN_AUTH_ERROR);

// 기존 버전
//        return UserSignupResponseDto.responseDto(StatusCode.BAD_REQUEST
//                , ResponseMessage.LOGIN_FAIL, null);

    }


    @PostMapping("/signup")
    public UserSignupResponseDto signup(@RequestBody @Validated UserSignupRequestDto userSignupRequestDto
            , @ApiIgnore Errors errors) {

        // 유효성 검증
        if (errors.hasErrors()) {
            return UserSignupResponseDto.responseDto(StatusCode.BAD_REQUEST
                    , errors.getFieldError().getDefaultMessage(), userSignupRequestDto);
        }


        return userService.signup(userSignupRequestDto);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto, response);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json",
                Charset.forName("UTF-8")));

        return new ResponseEntity<>(userLoginResponseDto, headers, userLoginResponseDto.getStatus());
    }

//    @PostMapping("/login")
//    public String login(@AuthenticationPrincipal UserDetails userDetails) {
//        System.out.println("*********************************************************");
//        System.out.println("UserController.login");
//        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
//        System.out.println("*********************************************************");
//
//        return "success";
//    }

}
