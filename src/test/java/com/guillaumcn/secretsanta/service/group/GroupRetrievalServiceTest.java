package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchGroupSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.GroupCreator.createGroup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupRetrievalServiceTest {

    public static final String GROUP_UUID = "GROUP_UUID";
    private static final String USER_UUID = "USER_UUID";
    private static final String GROUP_NAME = "GROUP_NAME";

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupRetrievalService groupRetrievalService;

    @Test
    void groupExists_findGroup_shouldReturnGroup() throws GroupNotFoundException {
        when(groupRepository.findById(GROUP_UUID)).thenReturn(Optional.of(createGroup(GROUP_UUID)));

        GroupEntity group = groupRetrievalService.findGroup(GROUP_UUID);

        assertEquals(GROUP_UUID, group.getUuid());
    }

    @Test
    void groupNotExists_findGroup_throwsGroupEntityNotFound() {
        when(groupRepository.findById(GROUP_UUID)).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> groupRetrievalService.findGroup(GROUP_UUID));
    }

    @Test
    void searchGroup_callFindAllWithSpecification() {
        // GIVEN
        List<GroupEntity> groups = Collections.emptyList();
        when(groupRepository.findAll(any(SearchGroupSpecification.class))).thenReturn(groups);
        SearchGroupRequest searchGroupRequest = SearchGroupRequest.builder()
                                                                  .ownerUuid(USER_UUID)
                                                                  .name(GROUP_NAME)
                                                                  .build();

        // WHEN
        List<GroupEntity> results = groupRetrievalService.searchGroups(searchGroupRequest);

        // THEN
        assertEquals(groups, results);

        ArgumentCaptor<SearchGroupSpecification> argumentCaptor = ArgumentCaptor.forClass(SearchGroupSpecification.class);
        verify(groupRepository).findAll(argumentCaptor.capture());
        SearchGroupSpecification searchGroupSpecification = argumentCaptor.getValue();
        assertEquals(USER_UUID, searchGroupSpecification.getOwnerUuid());
        assertEquals(GROUP_NAME, searchGroupSpecification.getName());
    }
}
