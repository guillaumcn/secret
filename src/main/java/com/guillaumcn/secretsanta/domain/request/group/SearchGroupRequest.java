package com.guillaumcn.secretsanta.domain.request.group;

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
public class SearchGroupRequest {

    private String name;
    private String ownerUuid;
}
