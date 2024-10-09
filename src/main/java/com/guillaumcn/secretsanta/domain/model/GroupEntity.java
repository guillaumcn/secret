package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GroupEntity extends BaseObjectEntity {

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_uuid")
    @NotNull
    private UserEntity owner;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();
}
