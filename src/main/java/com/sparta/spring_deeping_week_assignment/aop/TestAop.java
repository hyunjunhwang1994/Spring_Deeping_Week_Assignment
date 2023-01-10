package com.sparta.spring_deeping_week_assignment.aop;


import com.sparta.spring_deeping_week_assignment.entity.User;
import com.sparta.spring_deeping_week_assignment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TestAop {


    @Around("execution(public * com.sparta.spring_deeping_week_assignment.controller..*(..))")
    public synchronized Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

            // 핵심기능 수행
            Object output = joinPoint.proceed();



        log.info("TestAop Run");
            return output;

    }


}
