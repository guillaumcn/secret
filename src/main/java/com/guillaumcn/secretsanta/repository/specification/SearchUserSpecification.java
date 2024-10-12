package com.guillaumcn.secretsanta.repository.specification;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class SearchUserSpecification implements Specification<UserEntity> {

    private String email;
    private String lastName;
    private String firstName;

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

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

    public static SearchUserSpecification fromSearchRequest(SearchUserRequest searchUserRequest) {
        return builder()
                .email(searchUserRequest.getEmail())
                .lastName(searchUserRequest.getLastName())
                .firstName(searchUserRequest.getFirstName())
                .build();
    }
}
