package com.guillaumcn.secretsanta.domain.request.group;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateGroupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String ownerUuid;
}