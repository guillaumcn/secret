package com.guillaumcn.secretsanta.service.user;

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
    private final UserRetrievalService userRetrievalService;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        UserEntity createdUser = userRepository.save(UserMapper.mapToUserEntity(createUserRequest));
        return UserMapper.mapToCreateUserResponse(createdUser);
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUser(String userUuid) throws UserNotFoundException {
        UserEntity user = userRetrievalService.findUser(userUuid);
        return UserMapper.mapToGetUserResponse(user, true);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> searchUsers(SearchUserRequest searchUserRequest) {
        List<UserEntity> foundUsers = userRetrievalService.searchUsers(searchUserRequest);
        return foundUsers.stream().map((user -> UserMapper.mapToGetUserResponse(user, true))).toList();
    }

    @Transactional
    public void deleteUser(String userUuid) throws UserNotFoundException {
        userRepository.delete(userRetrievalService.findUser(userUuid));
    }

    @Transactional
    public void updateUser(String userUuid, UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        UserEntity user = userRetrievalService.findUser(userUuid);
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        user.setUpdatedAt(LocalDateTime.now());
    }
}
