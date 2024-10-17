package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.group.AssignGroupUserRequest;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.UpdateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import com.guillaumcn.secretsanta.mapper.GroupMapper;
import com.guillaumcn.secretsanta.mapper.UserMapper;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.service.user.UserRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupRetrievalService groupRetrievalService;
    private final UserRetrievalService userRetrievalService;

    @Transactional
    public CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest) throws UserNotFoundException {
        UserEntity ownerEntity = userRetrievalService.findUser(createGroupRequest.getOwnerUuid());
        GroupEntity createdGroup = groupRepository.save(GroupMapper.mapToGroupEntity(createGroupRequest, ownerEntity));
        return GroupMapper.mapToCreateGroupResponse(createdGroup);
    }

    @Transactional(readOnly = true)
    public GetGroupResponse getGroup(String groupUuid) throws GroupNotFoundException {
        GroupEntity group = groupRetrievalService.findGroup(groupUuid);
        return GroupMapper.mapToGetGroupResponse(group, true);
    }

    @Transactional(readOnly = true)
    public List<GetGroupResponse> searchGroups(SearchGroupRequest searchGroupRequest) {
        List<GroupEntity> foundGroups = groupRetrievalService.searchGroups(searchGroupRequest);
        return foundGroups.stream().map(foundGroup -> GroupMapper.mapToGetGroupResponse(foundGroup, true)).toList();
    }

    @Transactional
    public void deleteGroup(String groupUuid) throws GroupNotFoundException {
        groupRepository.delete(groupRetrievalService.findGroup(groupUuid));
    }

    @Transactional
    public void updateGroup(String groupUuid, UpdateGroupRequest updateGroupRequest) throws GroupNotFoundException {
        GroupEntity group = groupRetrievalService.findGroup(groupUuid);
        if (updateGroupRequest.getName() != null) {
            group.setName(updateGroupRequest.getName());
        }
        group.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional
    public void assignUserToGroup(String groupUuid, AssignGroupUserRequest assignGroupUserRequest) throws GroupNotFoundException, UserNotFoundException {
        GroupEntity group = groupRetrievalService.findGroup(groupUuid);
        UserEntity user = userRetrievalService.findUser(assignGroupUserRequest.getUserUuid());

        List<GroupEntity> currentGroups = user.getGroups();
        if (groupContains(currentGroups, group)) {
            return;
        }
        List<GroupEntity> newUserGroups = Stream.concat(currentGroups.stream(), Stream.of(group)).toList();
        user.setGroups(newUserGroups);

        group.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getGroupUsers(String groupUuid) throws GroupNotFoundException {
        return groupRetrievalService.findGroup(groupUuid).getUsers().stream().map((userEntity -> UserMapper.mapToGetUserResponse(userEntity, false))).toList();
    }

    private static boolean groupContains(List<GroupEntity> currentGroups, GroupEntity group) {
        return currentGroups.stream().map(GroupEntity::getUuid).toList().contains(group.getUuid());
    }
}
