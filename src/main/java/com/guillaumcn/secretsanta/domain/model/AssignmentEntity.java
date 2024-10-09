package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AssignmentEntity extends BaseAssignmentEntity {

    @OneToMany(mappedBy = "assignment")
    @Builder.Default
    private List<NoteEntity> notes = new ArrayList<>();
}
