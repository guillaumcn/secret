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
import com.guillaumcn.secretsanta.helper.AssignmentExceptionHelper;
import com.guillaumcn.secretsanta.mapper.AssignmentMapper;
import com.guillaumcn.secretsanta.repository.AssignmentRepository;
import com.guillaumcn.secretsanta.service.assignment.exception.AssignmentExceptionRetrievalService;
import com.guillaumcn.secretsanta.service.group.GroupRetrievalService;
import com.guillaumcn.secretsanta.validator.AssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRetrievalService assignmentRetrievalService;
    private final AssignmentRepository assignmentRepository;
    private final GroupRetrievalService groupRetrievalService;
    private final AssignmentExceptionRetrievalService assignmentExceptionRetrievalService;

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

    @Transactional
    public void createAssignments(CreateAssignmentsRequest createAssignmentsRequest) throws GroupNotFoundException, ImpossibleAssignmentException {
        GroupEntity group = groupRetrievalService.findGroup(createAssignmentsRequest.getGroupUuid());
        List<AssignmentExceptionEntity> assignmentExceptions = assignmentExceptionRetrievalService.findAssignmentExceptionsForGroup(createAssignmentsRequest.getGroupUuid());

        List<AssignmentEntity> assignments = getUserAssignments(group.getUsers(), assignmentExceptions, group);
        assignmentRepository.saveAll(assignments);
    }

    private static List<AssignmentEntity> getUserAssignments(List<UserEntity> users, List<AssignmentExceptionEntity> assignmentExceptions, GroupEntity group) throws ImpossibleAssignmentException {
        AssignmentValidator.assertAssignationIsPossible(users, assignmentExceptions);

        List<UserEntity> shuffledUsers = shuffleUntilValidAssignments(users, assignmentExceptions);
        return getAssignmentEntities(users, shuffledUsers, group);
    }

    private static List<AssignmentEntity> getAssignmentEntities(List<UserEntity> users, List<UserEntity> shuffledUsers, GroupEntity group) {
        List<AssignmentEntity> assignments = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            assignments.add(AssignmentEntity.builder()
                                            .sourceUser(users.get(i))
                                            .targetUser(shuffledUsers.get(i))
                                            .group(group)
                                            .build());
        }
        return assignments;
    }

    private static List<UserEntity> shuffleUntilValidAssignments(List<UserEntity> users, List<AssignmentExceptionEntity> assignmentExceptions) {
        List<UserEntity> shuffledUsers = new ArrayList<>(users);

        boolean shuffleOk;
        do {
            Collections.shuffle(shuffledUsers);
            shuffleOk = true;
            for (int i = 0; i < users.size(); i++) {
                UserEntity sourceUser = users.get(i);
                UserEntity targetUser = shuffledUsers.get(i);
                if (sourceUser.getUuid().equals(targetUser.getUuid()) || AssignmentExceptionHelper.isException(sourceUser, targetUser, assignmentExceptions)) {
                    shuffleOk = false;
                    break;
                }
            }
        } while (!shuffleOk);

        return shuffledUsers;
    }
}
