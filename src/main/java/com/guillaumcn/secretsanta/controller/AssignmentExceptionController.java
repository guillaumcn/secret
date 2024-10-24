package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.exception.AssignmentExceptionNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.CreateAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.SearchAssignmentExceptionRequest;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.CreateAssignmentExceptionResponse;
import com.guillaumcn.secretsanta.domain.response.assignment.exception.GetAssignmentExceptionResponse;
import com.guillaumcn.secretsanta.service.assignment.exception.AssignmentExceptionService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assignment-exceptions")
@RequiredArgsConstructor
public class AssignmentExceptionController {

    private final AssignmentExceptionService assignmentExceptionService;

    @PostMapping
    public CreateAssignmentExceptionResponse createAssignmentException(@RequestBody @Valid CreateAssignmentExceptionRequest createAssignmentExceptionRequest) throws UserNotFoundException, GroupNotFoundException {
        return assignmentExceptionService.createAssignmentException(createAssignmentExceptionRequest);
    }

    @GetMapping
    public List<GetAssignmentExceptionResponse> searchAssignmentExceptions(@Valid @Nullable SearchAssignmentExceptionRequest searchAssignmentExceptionRequest) {
        return assignmentExceptionService.searchAssignmentExceptions(searchAssignmentExceptionRequest);
    }

    @DeleteMapping("/{assignment_exception_uuid}")
    public void deleteAssignmentException(@PathVariable(name = "assignment_exception_uuid") String uuid) throws AssignmentExceptionNotFoundException {
        assignmentExceptionService.deleteAssignmentException(uuid);
    }
}
