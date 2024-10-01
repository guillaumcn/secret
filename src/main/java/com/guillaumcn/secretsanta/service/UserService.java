package com.guillaumcn.secretsanta.service;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.request.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.request.SearchUserRequest;
import com.guillaumcn.secretsanta.domain.response.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.SearchUserResponse;
import com.guillaumcn.secretsanta.model.UserEntity;
import com.guillaumcn.secretsanta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        UserEntity createdUser = userRepository.save(createUserRequest.toUserEntity());
        return createdUser.toCreateUserResponse();
    }

    @Transactional(readOnly = true)
    public List<SearchUserResponse> searchUsers(SearchUserRequest searchUserRequest) {
        List<UserEntity> foundUsers = userRepository.findAll(searchUserRequest.toSpecification());
        return foundUsers.stream().map(UserEntity::toSearchUserResponse).toList();
    }

    @Transactional
    public void deleteUser(String userUuid) throws UserNotFoundException {
        UserEntity user = userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
        userRepository.delete(user);
    }
}
