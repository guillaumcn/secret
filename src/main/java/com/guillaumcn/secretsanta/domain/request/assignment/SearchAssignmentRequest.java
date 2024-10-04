package com.guillaumcn.secretsanta.domain.request.assignment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchAssignmentRequest {

    private String sourceUserUuid;
    private String groupUuid;
}
