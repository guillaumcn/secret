package com.guillaumcn.secretsanta.repository;

import com.guillaumcn.secretsanta.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    @Override
    List<UserEntity> findAll();
}
