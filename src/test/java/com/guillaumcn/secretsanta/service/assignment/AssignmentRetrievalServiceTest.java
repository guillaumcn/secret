package com.guillaumcn.secretsanta.service.assignment;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
import com.guillaumcn.secretsanta.repository.AssignmentRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchAssignmentSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.AssignmentCreator.createAssignment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentRetrievalServiceTest {

    public static final String ASSIGNMENT_UUID = "ASSIGNMENT_UUID";
    private static final String USER_UUID = "USER_UUID";
    private static final String GROUP_UUID = "GROUP_UUID";

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentRetrievalService assignmentRetrievalService;

    @Test
    void assignmentExists_findAssignment_shouldReturnAssignment() throws AssignmentNotFoundException {
        when(assignmentRepository.findById(ASSIGNMENT_UUID)).thenReturn(Optional.of(createAssignment(ASSIGNMENT_UUID)));

        AssignmentEntity assignment = assignmentRetrievalService.findAssignment(ASSIGNMENT_UUID);

        assertEquals(ASSIGNMENT_UUID, assignment.getUuid());
    }

    @Test
    void assignmentNotExists_findAssignment_throwsAssignmentEntityNotFound() {
        when(assignmentRepository.findById(ASSIGNMENT_UUID)).thenReturn(Optional.empty());

        assertThrows(AssignmentNotFoundException.class, () -> assignmentRetrievalService.findAssignment(ASSIGNMENT_UUID));
    }

    @Test
    void searchAssignment_callFindAllWithSpecification() {
        // GIVEN
        List<AssignmentEntity> assignments = Collections.emptyList();
        when(assignmentRepository.findAll(any(SearchAssignmentSpecification.class))).thenReturn(assignments);
        SearchAssignmentRequest searchAssignmentRequest = SearchAssignmentRequest.builder()
                                                                                 .sourceUserUuid(USER_UUID)
                                                                                 .groupUuid(GROUP_UUID)
                                                                                 .build();

        // WHEN
        List<AssignmentEntity> results = assignmentRetrievalService.searchAssignment(searchAssignmentRequest);

        // THEN
        assertEquals(assignments, results);

        ArgumentCaptor<SearchAssignmentSpecification> argumentCaptor = ArgumentCaptor.forClass(SearchAssignmentSpecification.class);
        verify(assignmentRepository).findAll(argumentCaptor.capture());
        SearchAssignmentSpecification searchAssignmentSpecification = argumentCaptor.getValue();
        assertEquals(USER_UUID, searchAssignmentSpecification.getSourceUserUuid());
        assertEquals(GROUP_UUID, searchAssignmentSpecification.getGroupUuid());
    }
}
