package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignment_exception")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentExceptionEntity {

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

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
