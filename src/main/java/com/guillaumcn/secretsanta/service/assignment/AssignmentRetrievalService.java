package com.guillaumcn.secretsanta.service.assignment;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
import com.guillaumcn.secretsanta.repository.AssignmentRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchAssignmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentRetrievalService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentEntity findAssignment(String assignmentUuid) throws AssignmentNotFoundException {
        return assignmentRepository.findById(assignmentUuid).orElseThrow(() -> new AssignmentNotFoundException(assignmentUuid));
    }

    public List<AssignmentEntity> searchAssignment(SearchAssignmentRequest searchAssignmentRequest) {
        return assignmentRepository.findAll(SearchAssignmentSpecification.fromSearchRequest(searchAssignmentRequest));
    }
}
