package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.request.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.request.SearchUserRequest;
import com.guillaumcn.secretsanta.domain.response.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.SearchUserResponse;
import com.guillaumcn.secretsanta.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserResponse createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping
    public List<SearchUserResponse> searchUsers(@Valid SearchUserRequest searchUserRequest) {
        return userService.searchUsers(searchUserRequest);
    }

    @DeleteMapping("/{user_uuid}")
    public void deleteUser(@PathVariable(name = "user_uuid") String uuid) throws UserNotFoundException {
        userService.deleteUser(uuid);
    }
}
