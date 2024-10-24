package com.guillaumcn.secretsanta.domain.request.assignment;

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
public class SearchAssignmentRequest {

    private String sourceUserUuid;
    private String groupUuid;
}
