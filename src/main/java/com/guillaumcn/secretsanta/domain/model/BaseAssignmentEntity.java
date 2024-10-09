package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseAssignmentEntity extends BaseObjectEntity {

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
}
