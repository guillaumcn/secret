package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.service.user.UserRetrievalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
