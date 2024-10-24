package com.guillaumcn.secretsanta.service.user;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import com.guillaumcn.secretsanta.repository.UserRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchUserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRetrievalService {

    private final UserRepository userRepository;

    public UserEntity findUser(String userUuid) throws UserNotFoundException {
        return userRepository.findById(userUuid).orElseThrow(() -> new UserNotFoundException(userUuid));
    }

    public List<UserEntity> searchUsers(SearchUserRequest searchUserRequest) {
        return userRepository.findAll(SearchUserSpecification.fromSearchRequest(searchUserRequest));
    }
}
