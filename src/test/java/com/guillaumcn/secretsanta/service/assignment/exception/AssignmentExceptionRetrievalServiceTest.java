package com.guillaumcn.secretsanta.service.assignmentException.exception;

import com.guillaumcn.secretsanta.domain.exception.AssignmentExceptionNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.repository.AssignmentExceptionRepository;
import com.guillaumcn.secretsanta.service.assignment.exception.AssignmentExceptionRetrievalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.createAssignmentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentExceptionRetrievalServiceTest {

    public static final String ASSIGNMENT_EXCEPTION_UUID = "ASSIGNMENT_EXCEPTION_UUID";

    @Mock
    private AssignmentExceptionRepository assignmentExceptionRepository;

    @InjectMocks
    private AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;

    @Test
    public void assignmentExceptionExists_findAssignmentException_shouldReturnAssignmentException() throws AssignmentExceptionNotFoundException {
        when(assignmentExceptionRepository.findById(ASSIGNMENT_EXCEPTION_UUID)).thenReturn(Optional.of(createAssignmentException(ASSIGNMENT_EXCEPTION_UUID)));

        AssignmentExceptionEntity assignmentException = assignmentExceptionRetrievalService.findAssignmentException(ASSIGNMENT_EXCEPTION_UUID);

        assertEquals(ASSIGNMENT_EXCEPTION_UUID, assignmentException.getUuid());
    }

    @Test
    public void assignmentExceptionNotExists_findAssignmentException_throwsAssignmentExceptionEntityNotFound() {
        when(assignmentExceptionRepository.findById(ASSIGNMENT_EXCEPTION_UUID)).thenReturn(Optional.empty());

        assertThrows(AssignmentExceptionNotFoundException.class, () -> assignmentExceptionRetrievalService.findAssignmentException(ASSIGNMENT_EXCEPTION_UUID));
    }
}
