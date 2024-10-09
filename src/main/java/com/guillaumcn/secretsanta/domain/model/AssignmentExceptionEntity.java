package com.guillaumcn.secretsanta.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "assignment_exception")
@Getter
@NoArgsConstructor
@SuperBuilder
public class AssignmentExceptionEntity extends BaseAssignmentEntity {

}
