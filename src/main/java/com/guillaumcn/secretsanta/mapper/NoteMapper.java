package com.guillaumcn.secretsanta.mapper;

import com.guillaumcn.secretsanta.domain.model.NoteEntity;
import com.guillaumcn.secretsanta.domain.response.note.GetNoteResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoteMapper {

    public static GetNoteResponse mapToGetNote(NoteEntity noteEntity) {
        return GetNoteResponse.builder()
                              .uuid(noteEntity.getUuid())
                              .value(noteEntity.getValue())
                              .createdAt(noteEntity.getCreatedAt())
                              .updatedAt(noteEntity.getUpdatedAt())
                              .build();
    }
}
