package com.bknote71.ajouportal.controller;


import com.bknote71.ajouportal.dto.UserDto;
import com.bknote71.ajouportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserDto createUser(String username, String password, String name) {
        return userService.register(username, password, name);
    }
}
