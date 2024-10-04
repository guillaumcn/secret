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
import com.guillaumcn.secretsanta.mapper.AssignmentExceptionMapper;
import com.guillaumcn.secretsanta.repository.AssignmentExceptionRepository;
import com.guillaumcn.secretsanta.service.group.GroupRetrievalService;
import com.guillaumcn.secretsanta.service.user.UserRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentExceptionService {

    private final AssignmentExceptionRepository assignmentExceptionRepository;
    private final AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;
    private final UserRetrievalService userRetrievalService;
    private final GroupRetrievalService groupRetrievalService;

    @Transactional
    public CreateAssignmentExceptionResponse createAssignmentException(CreateAssignmentExceptionRequest createAssignmentExceptionRequest) throws UserNotFoundException, GroupNotFoundException {
        UserEntity sourceUser = userRetrievalService.findUser(createAssignmentExceptionRequest.getSourceUserUuid());
        UserEntity targetUser = userRetrievalService.findUser(createAssignmentExceptionRequest.getTargetUserUuid());
        GroupEntity group = groupRetrievalService.findGroup(createAssignmentExceptionRequest.getGroupUuid());
        AssignmentExceptionEntity createdAssignmentException = assignmentExceptionRepository.save(AssignmentExceptionMapper.mapToAssignmentExceptionEntity(sourceUser, targetUser, group));
        return AssignmentExceptionMapper.mapToCreateAssignmentExceptionResponse(createdAssignmentException);
    }

    @Transactional(readOnly = true)
    public List<GetAssignmentExceptionResponse> searchAssignmentExceptions(SearchAssignmentExceptionRequest searchAssignmentExceptionRequest) {
        List<AssignmentExceptionEntity> foundAssignmentExceptions = assignmentExceptionRetrievalService.searchAssignmentException(searchAssignmentExceptionRequest);
        return foundAssignmentExceptions.stream().map((AssignmentExceptionMapper::mapToGetAssignmentException)).toList();
    }

    @Transactional
    public void deleteAssignmentException(String assignmentExceptionUuid) throws AssignmentExceptionNotFoundException {
        assignmentExceptionRepository.delete(assignmentExceptionRetrievalService.findAssignmentException(assignmentExceptionUuid));
    }
}
