package com.guillaumcn.secretsanta.domain.request.assignment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchAssignmentExceptionRequest {

    private String userUuid;
    private String groupUuid;
}
