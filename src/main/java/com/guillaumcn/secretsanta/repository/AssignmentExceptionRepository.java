package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentExceptionRepository extends JpaRepository<AssignmentExceptionEntity, String> {

    List<AssignmentExceptionEntity> findAll(Specification<AssignmentExceptionEntity> specification);

}
