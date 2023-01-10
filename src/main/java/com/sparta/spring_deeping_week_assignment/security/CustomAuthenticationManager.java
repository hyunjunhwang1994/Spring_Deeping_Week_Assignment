//package com.sparta.spring_deeping_week_assignment.security;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@RequiredArgsConstructor
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//
//    private final UserDetailsServiceImpl userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//
//
//
//
//        String username = authentication.getPrincipal().toString();
//        String password = authentication.getCredentials().toString();
//
//
//
//        // 각종 처리를 구현
//        // 비번이 일치하는지
//        // 아이디로 회원을 조회 했을 때 존재하는 회원인지
//        // 기타 등등과 적절한 예외 처리
//        System.out.println("authenticate()");
//        if(username != null && password  != null){
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//
//            // 비밀번호 확인
//            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
//                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
//            }
//
//            // 인증 객체 생성 및 등록
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            context.setAuthentication(authentication);
//
//            SecurityContextHolder.setContext(context);
//            System.out.println("SecurityContext 생성 완료");
//
//
//
//
//            return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
//                    , userDetails.getPassword()
//                    , userDetails.getAuthorities());
//        }else {
//
//            return null;
//        }
//
//
//    }
//
//
//}
