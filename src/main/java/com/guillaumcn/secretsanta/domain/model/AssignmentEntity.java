package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "source_user_id")
    @NotNull
    private UserEntity sourceUser;

    @ManyToOne
    @JoinColumn(name = "target_user_id")
    @NotNull
    private UserEntity targetUser;

    @ManyToOne
    @JoinColumn(name = "group_uuid")
    @NotNull
    private GroupEntity group;

    @OneToMany(mappedBy = "assignment_uuid")
    @Builder.Default
    private List<NoteEntity> notes = new ArrayList<>();

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
