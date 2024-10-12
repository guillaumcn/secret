package com.guillaumcn.secretsanta.service.assignment.exception;

import com.guillaumcn.secretsanta.domain.exception.AssignmentExceptionNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.SearchAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.repository.AssignmentExceptionRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchAssignmentExceptionSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.AssignmentExceptionCreator.createAssignmentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentExceptionRetrievalServiceTest {

    private static final String ASSIGNMENT_EXCEPTION_UUID = "ASSIGNMENT_EXCEPTION_UUID";
    private static final String USER_UUID = "USER_UUID";
    private static final String GROUP_UUID = "GROUP_UUID";

    @Mock
    private AssignmentExceptionRepository assignmentExceptionRepository;

    @InjectMocks
    private AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;

    @Test
    void assignmentExceptionExists_findAssignmentException_shouldReturnAssignmentException() throws AssignmentExceptionNotFoundException {
        when(assignmentExceptionRepository.findById(ASSIGNMENT_EXCEPTION_UUID)).thenReturn(Optional.of(createAssignmentException(ASSIGNMENT_EXCEPTION_UUID)));

        AssignmentExceptionEntity assignmentException = assignmentExceptionRetrievalService.findAssignmentException(ASSIGNMENT_EXCEPTION_UUID);

        assertEquals(ASSIGNMENT_EXCEPTION_UUID, assignmentException.getUuid());
    }

    @Test
    void assignmentExceptionNotExists_findAssignmentException_throwsAssignmentExceptionEntityNotFound() {
        when(assignmentExceptionRepository.findById(ASSIGNMENT_EXCEPTION_UUID)).thenReturn(Optional.empty());

        assertThrows(AssignmentExceptionNotFoundException.class, () -> assignmentExceptionRetrievalService.findAssignmentException(ASSIGNMENT_EXCEPTION_UUID));
    }

    @Test
    void searchAssignmentException_callFindAllWithSpecification() {
        // GIVEN
        List<AssignmentExceptionEntity> assignmentExceptions = Collections.emptyList();
        when(assignmentExceptionRepository.findAll(any(SearchAssignmentExceptionSpecification.class))).thenReturn(assignmentExceptions);
        SearchAssignmentExceptionRequest searchAssignmentExceptionRequest = SearchAssignmentExceptionRequest.builder()
                                                                                                            .userUuid(USER_UUID)
                                                                                                            .groupUuid(GROUP_UUID)
                                                                                                            .build();

        // WHEN
        List<AssignmentExceptionEntity> results = assignmentExceptionRetrievalService.searchAssignmentException(searchAssignmentExceptionRequest);

        // THEN
        assertEquals(assignmentExceptions, results);

        ArgumentCaptor<SearchAssignmentExceptionSpecification> argumentCaptor = ArgumentCaptor.forClass(SearchAssignmentExceptionSpecification.class);
        verify(assignmentExceptionRepository).findAll(argumentCaptor.capture());
        SearchAssignmentExceptionSpecification searchAssignmentExceptionSpecification = argumentCaptor.getValue();
        assertEquals(USER_UUID, searchAssignmentExceptionSpecification.getUserUuid());
        assertEquals(GROUP_UUID, searchAssignmentExceptionSpecification.getGroupUuid());
    }

    @Test
    void searchAssignmentException_searchByGroupUuid_callRepository() {
        // GIVEN
        List<AssignmentExceptionEntity> assignmentExceptions = Collections.emptyList();
        when(assignmentExceptionRepository.findAllByGroupUuid(anyString())).thenReturn(assignmentExceptions);

        // WHEN
        List<AssignmentExceptionEntity> results = assignmentExceptionRetrievalService.findAssignmentExceptionsForGroup(GROUP_UUID);

        assertEquals(assignmentExceptions, results);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(assignmentExceptionRepository).findAllByGroupUuid(argumentCaptor.capture());
        assertEquals(GROUP_UUID, argumentCaptor.getValue());
    }
}
