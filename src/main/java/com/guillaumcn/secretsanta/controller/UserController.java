package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.request.user.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.UpdateUserRequest;
import com.guillaumcn.secretsanta.domain.response.user.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import com.guillaumcn.secretsanta.service.user.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public List<GetUserResponse> searchUsers(@Valid @Nullable SearchUserRequest searchUserRequest) {
        return userService.searchUsers(searchUserRequest);
    }

    @GetMapping("/{user_uuid}")
    public GetUserResponse getUser(@PathVariable(name = "user_uuid") String uuid) throws UserNotFoundException {
        return userService.getUser(uuid);
    }

    @DeleteMapping("/{user_uuid}")
    public void deleteUser(@PathVariable(name = "user_uuid") String uuid) throws UserNotFoundException {
        userService.deleteUser(uuid);
    }

    @PatchMapping("/{user_uuid}")
    public void patchUser(@PathVariable(name = "user_uuid") String uuid, @RequestBody @Valid UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        userService.updateUser(uuid, updateUserRequest);
    }
}
