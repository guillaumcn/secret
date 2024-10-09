package com.guillaumcn.secretsanta.service.assignment;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.repository.AssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.AssignmentCreator.createAssignment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentRetrievalServiceTest {

    public static final String ASSIGNMENT_UUID = "ASSIGNMENT_UUID";

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentRetrievalService assignmentRetrievalService;

    @Test
    public void assignmentExists_findAssignment_shouldReturnAssignment() throws AssignmentNotFoundException {
        when(assignmentRepository.findById(ASSIGNMENT_UUID)).thenReturn(Optional.of(createAssignment(ASSIGNMENT_UUID)));

        AssignmentEntity assignment = assignmentRetrievalService.findAssignment(ASSIGNMENT_UUID);

        assertEquals(ASSIGNMENT_UUID, assignment.getUuid());
    }

    @Test
    public void assignmentNotExists_findAssignment_throwsAssignmentEntityNotFound() {
        when(assignmentRepository.findById(ASSIGNMENT_UUID)).thenReturn(Optional.empty());

        assertThrows(AssignmentNotFoundException.class, () -> assignmentRetrievalService.findAssignment(ASSIGNMENT_UUID));
    }
}
