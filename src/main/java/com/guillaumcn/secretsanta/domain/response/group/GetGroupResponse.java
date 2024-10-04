package com.guillaumcn.secretsanta.domain.response.group;

import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupResponse {

    private String uuid;
    private String name;
    private GetUserResponse owner;
}