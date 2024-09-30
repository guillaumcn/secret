package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
