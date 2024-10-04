package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.response.user.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static GetUserResponse mapToGetUserResponse(UserEntity userEntity, boolean withGroups) {
        return GetUserResponse.builder()
                .uuid(userEntity.getUuid())
                .email(userEntity.getEmail())
                .lastName(userEntity.getLastName())
                .firstName(userEntity.getFirstName())
                .groups(withGroups ?
                        userEntity.getGroups().stream().map(GroupMapper::mapToGetGroupResponse).toList() :
                        null)
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    public static CreateUserResponse mapToCreateUserResponse(UserEntity userEntity) {
        return CreateUserResponse.builder()
                .uuid(userEntity.getUuid())
                .build();
    }

    public static UserEntity mapToUserEntity(CreateUserRequest createUserRequest) {
        return UserEntity.builder()
                .email(createUserRequest.getEmail())
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .password(createUserRequest.getPassword())
                .build();
    }
}
