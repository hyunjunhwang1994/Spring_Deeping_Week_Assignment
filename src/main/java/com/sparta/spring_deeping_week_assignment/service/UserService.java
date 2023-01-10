package com.sparta.spring_deeping_week_assignment.service;


import com.sparta.spring_deeping_week_assignment.dto.*;
import com.sparta.spring_deeping_week_assignment.entity.User;
import com.sparta.spring_deeping_week_assignment.entity.UserRoleEnum;
import com.sparta.spring_deeping_week_assignment.jwt.JwtUtil;
import com.sparta.spring_deeping_week_assignment.message.ResponseMessage;
import com.sparta.spring_deeping_week_assignment.message.StatusCode;
import com.sparta.spring_deeping_week_assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "dqp2020!)1)!zsjsjcmcmwml2XZNDk";

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        String username = userSignupRequestDto.getUsername();
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());


        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {

            throw new IllegalArgumentException(ResponseMessage.CREATED_USER_FAIL_ALREADY_EXISTS);
//            return UserSignupResponseDto.responseDto(StatusCode.BAD_REQUEST
//                    , ResponseMessage.CREATED_USER_FAIL_ALREADY_EXISTS, userSignupRequestDto );

        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(userSignupRequestDto.isAdmin()){

            if (!userSignupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;

        }

        User user = new User(username, password, role);
        userRepository.save(user);

        userSignupRequestDto.setAdminToken(null);

        return UserSignupResponseDto.responseDto(StatusCode.OK
                , ResponseMessage.CREATED_USER, userSignupRequestDto );



    }


    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto, HttpServletResponse response) {
        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();

        // 사용자 확인
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return new UserLoginResponseDto(StatusCode.BAD_REQUEST,
                    ResponseMessage.LOGIN_FAIL, null);
        }

        User user = optionalUser.get();


        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            return new UserLoginResponseDto(StatusCode.BAD_REQUEST,
                    ResponseMessage.LOGIN_FAIL, null);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new UserLoginResponseDto(StatusCode.OK,
                ResponseMessage.LOGIN_SUCCESS, user.getUsername());
    }

}
