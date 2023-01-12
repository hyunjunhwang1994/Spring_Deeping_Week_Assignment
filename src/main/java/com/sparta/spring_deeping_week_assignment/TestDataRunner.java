package com.sparta.spring_deeping_week_assignment;

import com.sparta.spring_deeping_week_assignment.entity.User;
import com.sparta.spring_deeping_week_assignment.entity.UserRoleEnum;
import com.sparta.spring_deeping_week_assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
//        User user1 = new User("user123", passwordEncoder.encode("Guswns@123"),  UserRoleEnum.USER);
//        userRepository.save(user1);


    }


}
