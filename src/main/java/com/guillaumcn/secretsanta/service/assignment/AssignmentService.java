package com.guillaumcn.secretsanta.service.assignment;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentResponse;
import com.guillaumcn.secretsanta.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRetrievalService assignmentRetrievalService;

    @Transactional(readOnly = true)
    public List<GetAssignmentResponse> searchAssignments(SearchAssignmentRequest searchAssignmentRequest) {
        List<AssignmentEntity> foundAssignments = assignmentRetrievalService.searchAssignment(searchAssignmentRequest);
        return foundAssignments.stream().map((assignmentEntity -> AssignmentMapper.mapToGetAssignment(assignmentEntity, false))).toList();
    }

    @Transactional(readOnly = true)
    public GetAssignmentResponse getAssignment(String assignmentUuid) throws AssignmentNotFoundException {
        AssignmentEntity assignment = assignmentRetrievalService.findAssignment(assignmentUuid);
        return AssignmentMapper.mapToGetAssignment(assignment, true);
    }
}
