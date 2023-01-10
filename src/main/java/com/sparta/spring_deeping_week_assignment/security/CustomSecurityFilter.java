//package com.sparta.spring_deeping_week_assignment.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.spring_deeping_week_assignment.dto.UserLoginRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.json.JSONObject;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.Struct;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public class CustomSecurityFilter extends OncePerRequestFilter {
//
//    private final UserDetailsServiceImpl userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        UserLoginRequestDto loginDto = objectMapper.readValue(
//                request.getReader().lines().collect(Collectors.joining()), UserLoginRequestDto.class);
//
//        String username = loginDto.getUsername();
//        String password = loginDto.getPassword();
//
//        System.out.println("username = " + username);
//        System.out.println("password = " + password);
//        System.out.println("request.getRequestURI() = " + request.getRequestURI());
//
//
//        if(username != null && password  != null && (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/api/test-secured"))){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            // 비밀번호 확인
//            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
//                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
//            }
//
//            // 인증 객체 생성 및 등록
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            context.setAuthentication(authentication);
//
//            SecurityContextHolder.setContext(context);
//        }
//
//        filterChain.doFilter(request,response);
//    }
//}