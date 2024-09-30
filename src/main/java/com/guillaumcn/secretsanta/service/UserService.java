package com.guillaumcn.secretsanta.service;

import com.guillaumcn.secretsanta.model.GroupEntity;
import com.guillaumcn.secretsanta.model.UserEntity;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public UserEntity createUser() {
        UserEntity user = UserEntity.builder()
                .email("aaa@aaa.aaa")
                .firstName("First name")
                .lastName("Last name")
                .password("aaaa")
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public GroupEntity createGroup(UserEntity user) {
        GroupEntity group = GroupEntity.builder()
                .name("aaa")
                .owner(user)
                .build();

        return groupRepository.save(group);
    }

    @Transactional
    public void addGroupToUser(UserEntity user, GroupEntity group) {
        user.getGroups().add(group);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
