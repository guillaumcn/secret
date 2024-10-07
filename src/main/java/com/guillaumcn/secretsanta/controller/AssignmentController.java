package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.exception.AssignmentNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.ImpossibleAssignmentException;
import com.guillaumcn.secretsanta.domain.request.assignment.CreateAssignmentsRequest;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentResponse;
import com.guillaumcn.secretsanta.service.assignment.AssignmentService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    public List<GetAssignmentResponse> searchAssignments(@Valid @Nullable SearchAssignmentRequest searchAssignmentRequest) {
        return assignmentService.searchAssignments(searchAssignmentRequest);
    }

    @GetMapping("/{assignment_uuid}")
    public GetAssignmentResponse getAssignment(@PathVariable(name = "assignment_uuid") String uuid) throws AssignmentNotFoundException {
        return assignmentService.getAssignment(uuid);
    }

    @PostMapping
    public void createAssignments(@RequestBody @Valid CreateAssignmentsRequest createAssignmentsRequest) throws GroupNotFoundException, ImpossibleAssignmentException {
        assignmentService.createAssignments(createAssignmentsRequest);
    }
}
