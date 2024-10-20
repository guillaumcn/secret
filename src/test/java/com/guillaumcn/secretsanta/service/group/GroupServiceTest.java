package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.UpdateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.service.user.UserRetrievalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    public static final String OWNER_USER_UUID = "OWNER_USER_UUID";
    public static final String GROUP_NAME = "GROUP_NAME";
    public static final String GROUP_UUID = "GROUP_UUID";

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRetrievalService userRetrievalService;

    @Mock
    private GroupRetrievalService groupRetrievalService;

    @InjectMocks
    private GroupService groupService;

    @Test
    void testCreateGroup() throws UserNotFoundException {
        // GIVEN
        UserEntity ownerUser = UserEntity.builder()
                                         .uuid(OWNER_USER_UUID)
                                         .build();
        CreateGroupRequest createGroupRequest = CreateGroupRequest.builder()
                                                                  .ownerUuid(OWNER_USER_UUID)
                                                                  .name(GROUP_NAME)
                                                                  .build();
        when(userRetrievalService.findUser(OWNER_USER_UUID)).thenReturn(ownerUser);
        when(groupRepository.save(any(GroupEntity.class))).thenAnswer((invocation) -> {
            GroupEntity argument = invocation.getArgument(0);
            return GroupEntity.builder()
                              .owner(argument.getOwner())
                              .name(argument.getName())
                              .uuid(GROUP_UUID)
                              .build();
        });

        // WHEN
        CreateGroupResponse response = groupService.createGroup(createGroupRequest);

        // THEN
        ArgumentCaptor<GroupEntity> argumentCaptor = ArgumentCaptor.forClass(GroupEntity.class);
        verify(groupRepository).save(argumentCaptor.capture());
        GroupEntity groupEntityArgument = argumentCaptor.getValue();
        assertNull(groupEntityArgument.getUuid());
        assertEquals(OWNER_USER_UUID, groupEntityArgument.getOwner().getUuid());
        assertEquals(GROUP_NAME, groupEntityArgument.getName());
        assertEquals(GROUP_UUID, response.getUuid());
    }

    @Test
    void testGetGroup() throws GroupNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        GroupEntity group = createGroup(now);

        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);

        // WHEN
        GetGroupResponse response = groupService.getGroup(GROUP_UUID);

        // THEN
        assertEquals(GROUP_UUID, response.getUuid());
        assertEquals(GROUP_NAME, response.getName());
        assertEquals(OWNER_USER_UUID, response.getOwner().getUuid());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
    }

    @Test
    void testSearchGroup() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        GroupEntity group = createGroup(now);
        SearchGroupRequest searchGroupRequest = SearchGroupRequest
                .builder()
                .name(GROUP_NAME)
                .build();

        when(groupRetrievalService.searchGroups(any(SearchGroupRequest.class))).thenReturn(Collections.singletonList(group));

        // WHEN
        List<GetGroupResponse> response = groupService.searchGroups(searchGroupRequest);

        // THEN
        assertEquals(1, response.size());
        GetGroupResponse firstResponse = response.getFirst();
        assertEquals(GROUP_UUID, firstResponse.getUuid());
        assertEquals(GROUP_NAME, firstResponse.getName());
        assertEquals(OWNER_USER_UUID, firstResponse.getOwner().getUuid());
        assertEquals(now, firstResponse.getCreatedAt());
        assertEquals(now, firstResponse.getUpdatedAt());
    }

    @Test
    void testDeleteGroup() throws GroupNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        GroupEntity group = createGroup(now);

        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);

        // WHEN
        groupService.deleteGroup(GROUP_UUID);

        // THEN
        verify(groupRepository).delete(group);
    }

    @Test
    void updateAll_updateGroup_shouldUpdateEachFields() throws GroupNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        GroupEntity group = createGroup(now);

        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);

        final String newName = "NEW_NAME";
        UpdateGroupRequest updateGroupRequest = UpdateGroupRequest.builder()
                                                                  .name(newName)
                                                                  .build();

        // WHEN
        groupService.updateGroup(GROUP_UUID, updateGroupRequest);

        // THEN
        assertEquals(newName, group.getName());
        assertNotEquals(now, group.getUpdatedAt());
    }

    @Test
    void updateNone_updateGroup_shouldUpdateOnlyUpdatedAt() throws GroupNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        GroupEntity group = createGroup(now);

        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);

        UpdateGroupRequest updateGroupRequest = UpdateGroupRequest.builder()
                                                                  .build();

        // WHEN
        groupService.updateGroup(GROUP_UUID, updateGroupRequest);

        // THEN
        assertEquals(GROUP_NAME, group.getName());
        assertNotEquals(now, group.getUpdatedAt());
    }

    private static GroupEntity createGroup(LocalDateTime now) {
        UserEntity owner = createUser(OWNER_USER_UUID);

        return GroupEntity.builder()
                          .name(GROUP_NAME)
                          .owner(owner)
                          .uuid(GROUP_UUID)
                          .createdAt(now)
                          .updatedAt(now)
                          .build();
    }
}
