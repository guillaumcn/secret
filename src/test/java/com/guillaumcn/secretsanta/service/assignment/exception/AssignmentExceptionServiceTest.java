package com.guillaumcn.secretsanta.service.assignment.exception;

import com.guillaumcn.secretsanta.domain.exception.AssignmentExceptionNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.CreateAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.SearchAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.CreateAssignmentExceptionResponse;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentExceptionResponse;
import com.guillaumcn.secretsanta.repository.AssignmentExceptionRepository;
import com.guillaumcn.secretsanta.service.group.GroupRetrievalService;
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

import static com.guillaumcn.secretsanta.creator.GroupCreator.createGroup;
import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentExceptionServiceTest {

    public static final String SOURCE_USER_UUID = "SOURCE_USER_UUID";
    public static final String TARGET_USER_UUID = "TARGET_USER_UUID";
    public static final String GROUP_UUID = "GROUP_UUID";
    public static final String ASSIGNMENT_EXCEPTION_UUID = "ASSIGNMENT_EXCEPTION_UUID";
    @Mock
    private AssignmentExceptionRepository assignmentExceptionRepository;

    @Mock
    private UserRetrievalService userRetrievalService;

    @Mock
    private GroupRetrievalService groupRetrievalService;

    @Mock
    private AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;

    @InjectMocks
    private AssignmentExceptionService assignmentExceptionService;

    @Test
    void testCreateAssignmentException() throws UserNotFoundException, GroupNotFoundException {
        // GIVEN
        UserEntity sourceUser = UserEntity.builder()
                                          .uuid(SOURCE_USER_UUID)
                                          .build();
        UserEntity targetUser = UserEntity.builder()
                                          .uuid(TARGET_USER_UUID)
                                          .build();

        GroupEntity group = GroupEntity.builder()
                                       .uuid(GROUP_UUID)
                                       .build();
        CreateAssignmentExceptionRequest createAssignmentExceptionRequest = CreateAssignmentExceptionRequest.builder()
                                                                                                            .groupUuid(GROUP_UUID)
                                                                                                            .sourceUserUuid(SOURCE_USER_UUID)
                                                                                                            .targetUserUuid(TARGET_USER_UUID)
                                                                                                            .build();
        when(userRetrievalService.findUser(SOURCE_USER_UUID)).thenReturn(sourceUser);
        when(userRetrievalService.findUser(TARGET_USER_UUID)).thenReturn(targetUser);
        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);
        when(assignmentExceptionRepository.save(any(AssignmentExceptionEntity.class))).thenAnswer((invocation) -> {
            AssignmentExceptionEntity argument = invocation.getArgument(0);
            return AssignmentExceptionEntity.builder()
                                            .sourceUser(argument.getSourceUser())
                                            .targetUser(argument.getTargetUser())
                                            .group(argument.getGroup())
                                            .uuid(ASSIGNMENT_EXCEPTION_UUID)
                                            .build();
        });

        // WHEN
        CreateAssignmentExceptionResponse response = assignmentExceptionService.createAssignmentException(createAssignmentExceptionRequest);

        // THEN
        ArgumentCaptor<AssignmentExceptionEntity> argumentCaptor = ArgumentCaptor.forClass(AssignmentExceptionEntity.class);
        verify(assignmentExceptionRepository).save(argumentCaptor.capture());
        AssignmentExceptionEntity assignmentExceptionEntityArgument = argumentCaptor.getValue();
        assertNull(assignmentExceptionEntityArgument.getUuid());
        assertEquals(SOURCE_USER_UUID, assignmentExceptionEntityArgument.getSourceUser().getUuid());
        assertEquals(TARGET_USER_UUID, assignmentExceptionEntityArgument.getTargetUser().getUuid());
        assertEquals(GROUP_UUID, assignmentExceptionEntityArgument.getGroup().getUuid());
        assertEquals(ASSIGNMENT_EXCEPTION_UUID, response.getUuid());
    }

    @Test
    void testSearchAssignmentException() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        AssignmentExceptionEntity assignmentException = createAssignmentException(now);
        SearchAssignmentExceptionRequest searchAssignmentExceptionRequest = SearchAssignmentExceptionRequest
                .builder()
                .userUuid(SOURCE_USER_UUID)
                .build();

        when(assignmentExceptionRetrievalService.searchAssignmentException(any(SearchAssignmentExceptionRequest.class))).thenReturn(Collections.singletonList(assignmentException));

        // WHEN
        List<GetAssignmentExceptionResponse> response = assignmentExceptionService.searchAssignmentExceptions(searchAssignmentExceptionRequest);

        // THEN
        assertEquals(1, response.size());
        GetAssignmentExceptionResponse firstResponse = response.getFirst();
        assertEquals(ASSIGNMENT_EXCEPTION_UUID, firstResponse.getUuid());
        assertEquals(GROUP_UUID, firstResponse.getGroup().getUuid());
        assertEquals(SOURCE_USER_UUID, firstResponse.getSourceUser().getUuid());
        assertEquals(TARGET_USER_UUID, firstResponse.getTargetUser().getUuid());
        assertEquals(now, firstResponse.getCreatedAt());
        assertEquals(now, firstResponse.getUpdatedAt());
    }

    @Test
    void testDeleteGroup() throws AssignmentExceptionNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        AssignmentExceptionEntity assignmentException = createAssignmentException(now);

        when(assignmentExceptionRetrievalService.findAssignmentException(ASSIGNMENT_EXCEPTION_UUID)).thenReturn(assignmentException);

        // WHEN
        assignmentExceptionService.deleteAssignmentException(ASSIGNMENT_EXCEPTION_UUID);

        // THEN
        verify(assignmentExceptionRepository).delete(assignmentException);
    }

    private static AssignmentExceptionEntity createAssignmentException(LocalDateTime now) {
        UserEntity sourceUser = createUser(SOURCE_USER_UUID);
        UserEntity targetUser = createUser(TARGET_USER_UUID);
        GroupEntity group = createGroup(GROUP_UUID);

        return AssignmentExceptionEntity.builder()
                                        .uuid(ASSIGNMENT_EXCEPTION_UUID)
                                        .sourceUser(sourceUser)
                                        .targetUser(targetUser)
                                        .group(group)
                                        .createdAt(now)
                                        .updatedAt(now)
                                        .build();
    }
}
