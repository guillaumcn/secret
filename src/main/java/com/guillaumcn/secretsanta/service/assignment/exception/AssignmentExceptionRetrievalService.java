package com.guillaumcn.secretsanta.service.assignment.exception;

import com.guillaumcn.secretsanta.domain.exception.AssignmentExceptionNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.SearchAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.repository.AssignmentExceptionRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchAssignmentExceptionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentExceptionRetrievalService {

    private final AssignmentExceptionRepository assignmentExceptionRepository;

    public AssignmentExceptionEntity findAssignmentException(String assignmentExceptionUuid) throws AssignmentExceptionNotFoundException {
        return assignmentExceptionRepository.findById(assignmentExceptionUuid).orElseThrow(() -> new AssignmentExceptionNotFoundException(assignmentExceptionUuid));
    }

    public List<AssignmentExceptionEntity> findAssignmentExceptionsForGroup(String groupUuid) {
        return assignmentExceptionRepository.findAllByGroupUuid(groupUuid);
    }

    public List<AssignmentExceptionEntity> searchAssignmentException(SearchAssignmentExceptionRequest searchAssignmentExceptionRequest) {
        return assignmentExceptionRepository.findAll(SearchAssignmentExceptionSpecification.fromSearchRequest(searchAssignmentExceptionRequest));
    }
}
