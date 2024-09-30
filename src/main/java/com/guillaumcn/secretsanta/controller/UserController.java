package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.model.GroupEntity;
import com.guillaumcn.secretsanta.model.UserEntity;
import com.guillaumcn.secretsanta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser() {
        UserEntity user = userService.createUser();
        GroupEntity group = userService.createGroup(user);
        userService.addGroupToUser(user, group);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.findAll();
    }
}
