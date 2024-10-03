package com.guillaumcn.secretsanta.service;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.UpdateUserRequest;
import com.guillaumcn.secretsanta.domain.response.user.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import com.guillaumcn.secretsanta.mapper.UserMapper;
import com.guillaumcn.secretsanta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity createdUser = userRepository.save(UserMapper.mapToUserEntity(createUserRequest));
        createdUser.setCreatedAt(now);
        createdUser.setUpdatedAt(now);
        return UserMapper.mapToCreateUserResponse(createdUser);
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUser(String userUuid) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
        return UserMapper.mapToGetUserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> searchUsers(SearchUserRequest searchUserRequest) {
        List<UserEntity> foundUsers = userRepository.findAll(searchUserRequest.toSpecification());
        return foundUsers.stream().map(UserMapper::mapToGetUserResponse).toList();
    }

    @Transactional
    public void deleteUser(String userUuid) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUser(String userUuid, UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        user.setUpdatedAt(now);
    }
}
