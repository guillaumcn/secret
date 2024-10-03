package com.guillaumcn.secretsanta.repository.specification;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class SearchUserSpecification implements Specification<UserEntity> {

    private String uuid;
    private String email;
    private String lastName;
    private String firstName;

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(uuid)) {
            predicates.add(criteriaBuilder.equal(root.get("uuid"), uuid));
        }

        if (Objects.nonNull(email)) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
        }

        if (Objects.nonNull(lastName)) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%"));
        }

        if (Objects.nonNull(firstName)) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
