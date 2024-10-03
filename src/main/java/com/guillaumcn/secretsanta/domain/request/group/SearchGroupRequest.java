package com.guillaumcn.secretsanta.domain.request.group;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SearchGroupRequest {

    private String name;
    private String ownerUuid;
}
