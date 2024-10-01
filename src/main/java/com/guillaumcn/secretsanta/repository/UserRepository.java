package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.model.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    List<UserEntity> findAll(Specification<UserEntity> specification);
}
