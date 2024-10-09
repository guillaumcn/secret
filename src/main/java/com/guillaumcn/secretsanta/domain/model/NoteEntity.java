package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "note")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteEntity extends BaseObjectEntity {

    @NotNull
    private String value;

    @ManyToOne
    @JoinColumn(name = "assignment_uuid")
    @NotNull
    private AssignmentEntity assignment;
}
