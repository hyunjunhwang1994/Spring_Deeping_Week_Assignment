package com.sparta.spring_deeping_week_assignment.config;


import com.sparta.spring_deeping_week_assignment.jwt.JwtAuthFilter;
import com.sparta.spring_deeping_week_assignment.jwt.JwtUtil;
import com.sparta.spring_deeping_week_assignment.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
//@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    // swagger설정
    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    private final JwtUtil jwtUtil;

    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {


        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();



        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
//                .antMatchers("/api/login-page").permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                .and()
//                .addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);

        http.formLogin().loginPage("/api/login-page").permitAll();

//        http.formLogin().disable();
//        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//



//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }

//
//    protected CustomUsernamePasswordAuthenticationFilter getAuthenticationFilter() {
//        CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
//        try {
//            authFilter.setFilterProcessesUrl("/api/login");
//            authFilter.setAuthenticationManager(customAuthenticationManager());
//            authFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler("/api"));
////            authFilter.setUsernameParameter("username");
////            authFilter.setPasswordParameter("password");
////            authFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler("/login"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return authFilter;
//    }
////     커스텀 인증 매니저
//    @Bean
//    public CustomAuthenticationManager customAuthenticationManager() {
//        return new CustomAuthenticationManager(userDetailsService, passwordEncoder());
//    }




}
