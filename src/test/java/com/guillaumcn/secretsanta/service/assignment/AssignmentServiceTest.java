package com.guillaumcn.secretsanta.service.assignment;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.ImpossibleAssignmentException;
import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.CreateAssignmentsRequest;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentResponse;
import com.guillaumcn.secretsanta.repository.AssignmentRepository;
import com.guillaumcn.secretsanta.service.assignment.exception.AssignmentExceptionRetrievalService;
import com.guillaumcn.secretsanta.service.group.GroupRetrievalService;
import com.guillaumcn.secretsanta.utils.ListShuffler;
import com.guillaumcn.secretsanta.validator.AssignmentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.createAssignmentException;
import static com.guillaumcn.secretsanta.creator.GroupCreator.createGroup;
import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    public static final String SOURCE_USER_UUID = "SOURCE_USER_UUID";
    public static final String TARGET_USER_UUID = "TARGET_USER_UUID";
    public static final String GROUP_UUID = "GROUP_UUID";
    public static final String ASSIGNMENT_UUID = "ASSIGNMENT_UUID";
    @Mock
    private AssignmentRetrievalService assignmentRetrievalService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private GroupRetrievalService groupRetrievalService;

    @Mock
    private AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;

    @Mock
    private AssignmentValidator assignmentValidator;

    @Mock
    private ListShuffler<UserEntity> listShuffler;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void testGetAssignment() throws AssignmentNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        AssignmentEntity assignment = createAssignment(now);

        when(assignmentRetrievalService.findAssignment(ASSIGNMENT_UUID)).thenReturn(assignment);

        // WHEN
        GetAssignmentResponse response = assignmentService.getAssignment(ASSIGNMENT_UUID);

        // THEN
        assertEquals(ASSIGNMENT_UUID, response.getUuid());
        assertEquals(GROUP_UUID, response.getGroup().getUuid());
        assertEquals(SOURCE_USER_UUID, response.getSourceUser().getUuid());
        assertEquals(TARGET_USER_UUID, response.getTargetUser().getUuid());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
    }

    @Test
    void testSearchAssignmentException() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        AssignmentEntity assignment = createAssignment(now);
        SearchAssignmentRequest searchAssignmentRequest = SearchAssignmentRequest
                .builder()
                .sourceUserUuid(SOURCE_USER_UUID)
                .build();

        when(assignmentRetrievalService.searchAssignment(any(SearchAssignmentRequest.class))).thenReturn(Collections.singletonList(assignment));

        // WHEN
        List<GetAssignmentResponse> response = assignmentService.searchAssignments(searchAssignmentRequest);

        // THEN
        assertEquals(1, response.size());
        GetAssignmentResponse firstResponse = response.getFirst();
        assertEquals(ASSIGNMENT_UUID, firstResponse.getUuid());
        assertEquals(GROUP_UUID, firstResponse.getGroup().getUuid());
        assertEquals(SOURCE_USER_UUID, firstResponse.getSourceUser().getUuid());
        assertEquals(TARGET_USER_UUID, firstResponse.getTargetUser().getUuid());
        assertEquals(now, firstResponse.getCreatedAt());
        assertEquals(now, firstResponse.getUpdatedAt());
    }

    @Test
    void assignationNotPossible_createAssignments_throwsException() throws ImpossibleAssignmentException, GroupNotFoundException {
        // GIVEN
        CreateAssignmentsRequest createAssignmentsRequest = CreateAssignmentsRequest.builder()
                                                                                    .groupUuid(GROUP_UUID)
                                                                                    .build();

        UserEntity unassignableUser = createUser(SOURCE_USER_UUID);
        GroupEntity group = createGroup(GROUP_UUID);
        ImpossibleAssignmentException exception = new ImpossibleAssignmentException(unassignableUser);
        doThrow(exception).when(assignmentValidator).assertAssignationIsPossible(anyList(), anyList());
        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);
        when(assignmentExceptionRetrievalService.findAssignmentExceptionsForGroup(GROUP_UUID)).thenReturn(Collections.emptyList());

        // WHEN THEN
        assertThrows(ImpossibleAssignmentException.class, () -> assignmentService.createAssignments(createAssignmentsRequest));
    }

    @Test
    void assignationPossible_createAssignments_saveAssignmentEntities() throws ImpossibleAssignmentException, GroupNotFoundException {
        // GIVEN
        final String user1Uuid = "USER_1_UUID";
        UserEntity firstUser = createUser(user1Uuid);
        final String user2Uuid = "USER_2_UUID";
        UserEntity secondUser = createUser(user2Uuid);
        final String user3Uuid = "USER_3_UUID";
        UserEntity thirdUser = createUser(user3Uuid);
        AssignmentExceptionEntity assignmentException = createAssignmentException(firstUser, secondUser);
        List<UserEntity> inputUsers = List.of(firstUser, secondUser, thirdUser);
        List<UserEntity> sameIndexListUsers = List.of(firstUser, thirdUser, secondUser);
        List<UserEntity> assignmentExceptionListUsers = List.of(secondUser, thirdUser, firstUser);
        List<UserEntity> validAssignmentList = List.of(thirdUser, firstUser, secondUser);
        prepareListShufflerResponses(List.of(sameIndexListUsers, assignmentExceptionListUsers, validAssignmentList));

        CreateAssignmentsRequest createAssignmentsRequest = CreateAssignmentsRequest.builder()
                                                                                    .groupUuid(GROUP_UUID)
                                                                                    .build();

        GroupEntity group = createGroup(GROUP_UUID, inputUsers);
        when(groupRetrievalService.findGroup(GROUP_UUID)).thenReturn(group);
        when(assignmentExceptionRetrievalService.findAssignmentExceptionsForGroup(GROUP_UUID)).thenReturn(Collections.singletonList(assignmentException));

        // WHEN
        assignmentService.createAssignments(createAssignmentsRequest);

        // THEN
        ArgumentCaptor<List<AssignmentEntity>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(assignmentRepository).saveAll(argumentCaptor.capture());
        List<AssignmentEntity> savedList = argumentCaptor.getValue();

        assertEquals(3, savedList.size());
        assertEquals(user1Uuid, savedList.getFirst().getSourceUser().getUuid());
        assertEquals(user3Uuid, savedList.getFirst().getTargetUser().getUuid());
        assertEquals(user2Uuid, savedList.get(1).getSourceUser().getUuid());
        assertEquals(user1Uuid, savedList.get(1).getTargetUser().getUuid());
        assertEquals(user3Uuid, savedList.get(2).getSourceUser().getUuid());
        assertEquals(user2Uuid, savedList.get(2).getTargetUser().getUuid());
    }

    private static AssignmentEntity createAssignment(LocalDateTime now) {
        UserEntity sourceUser = createUser(SOURCE_USER_UUID);
        UserEntity targetUser = createUser(TARGET_USER_UUID);
        GroupEntity group = createGroup(GROUP_UUID);

        return AssignmentEntity.builder()
                               .uuid(ASSIGNMENT_UUID)
                               .sourceUser(sourceUser)
                               .targetUser(targetUser)
                               .group(group)
                               .createdAt(now)
                               .updatedAt(now)
                               .build();
    }

    private void prepareListShufflerResponses(List<List<UserEntity>> shuffleResponses) {
        AtomicInteger shuffleCallIncrement = new AtomicInteger();
        when(listShuffler.shuffle(anyList())).thenAnswer((invocation) -> shuffleResponses.get(shuffleCallIncrement.getAndIncrement()));
    }
}
