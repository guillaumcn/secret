package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity, String> {
    List<AssignmentEntity> findAll(Specification<AssignmentEntity> specification);

}
