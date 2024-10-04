package com.guillaumcn.secretsanta.domain.response.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateAssignmentExceptionResponse {

    private String uuid;
}
