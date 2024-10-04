package com.guillaumcn.secretsanta.domain.response.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetNoteResponse {

    private String uuid;
    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
