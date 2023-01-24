package com.bknote71.ajouportal.security;

import com.bknote71.ajouportal.domain.Resource;
import com.bknote71.ajouportal.domain.UserEntity;
import com.bknote71.ajouportal.repository.ResourceRepository;
import com.bknote71.ajouportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSecurityInitializer implements ApplicationRunner {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Resource r1 = Resource.builder()
                .role("ROLE_STUDENT")
                .type("url")
                .content("/")
                .build();

        Resource r2 = Resource.builder()
                .role("ROLE_STUDENT")
                .type("url")
                .content("/sugang/**")
                .build();

        resourceRepository.save(r1);
        resourceRepository.save(r2);

        UserEntity build = UserEntity.builder()
                .role("ROLE_STUDENT")
                .name("bk")
                .username("root")
                .password(passwordEncoder.encode("root"))
                .build();

        userRepository.save(build);
    }
}
