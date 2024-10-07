package com.guillaumcn.secretsanta.domain.request.assignment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateAssignmentsRequest {

    @NotBlank
    private String groupUuid;
}
