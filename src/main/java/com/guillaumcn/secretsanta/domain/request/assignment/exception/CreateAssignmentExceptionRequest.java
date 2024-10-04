package com.guillaumcn.secretsanta.domain.request.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateAssignmentExceptionRequest {
    private String sourceUserUuid;
    private String targetUserUuid;
    private String groupUuid;
}
