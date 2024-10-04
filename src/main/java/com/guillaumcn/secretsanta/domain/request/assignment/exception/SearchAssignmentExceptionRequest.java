package com.guillaumcn.secretsanta.domain.request.assignment.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchAssignmentExceptionRequest {

    private String userUuid;
    private String groupUuid;
}
