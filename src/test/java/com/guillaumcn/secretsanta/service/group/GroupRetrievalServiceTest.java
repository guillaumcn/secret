package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.GroupCreator.createGroup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupRetrievalServiceTest {

    public static final String GROUP_UUID = "GROUP_UUID";

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
}
