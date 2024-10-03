package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    List<GroupEntity> findAll(Specification<GroupEntity> specification);
}
