//package com.sparta.spring_deeping_week_assignment.security;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    private String defaultSuccessUrl;
//
//    public CustomAuthenticationSuccessHandler(String defaultSuccessUrl) {
//        this.defaultSuccessUrl = defaultSuccessUrl;
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//        // 성공이후 로그를 님긴다
//        // 성공이벤트를 발행한다.
//        // 이메일을 발송한다.
//        response.sendRedirect(defaultSuccessUrl);
//    }
//}
