package com.guillaumcn.secretsanta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
