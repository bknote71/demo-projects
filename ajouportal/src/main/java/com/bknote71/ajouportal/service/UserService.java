package com.bknote71.ajouportal.service;

import com.bknote71.ajouportal.domain.UserEntity;
import com.bknote71.ajouportal.dto.UserDto;
import com.bknote71.ajouportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(String username, String password, String name) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new IllegalArgumentException("이미 있는 유저입니다. 다른 아이디로 생성 부탁드립니다.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role("ROLE_STUDENT")
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        return new UserDto(savedUser.getId(), savedUser.getName());
    }
}
