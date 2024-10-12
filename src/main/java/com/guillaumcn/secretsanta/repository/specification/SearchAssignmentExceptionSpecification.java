package com.guillaumcn.secretsanta.repository.specification;

import com.guillaumcn.secretsanta.domain.model.AssignmentExceptionEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.exception.SearchAssignmentExceptionRequest;
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
public class SearchAssignmentExceptionSpecification implements Specification<AssignmentExceptionEntity> {

    private String groupUuid;
    private String userUuid;

    @Override
    public Predicate toPredicate(Root<AssignmentExceptionEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(userUuid)) {
            List<Predicate> userPredicates = List.of(
                    criteriaBuilder.equal(root.get("sourceUser").get("uuid"), userUuid),
                    criteriaBuilder.equal(root.get("targetUser").get("uuid"), userUuid)
            );
            predicates.add(criteriaBuilder.or(userPredicates.toArray(new Predicate[0])));
        }

        if (Objects.nonNull(groupUuid)) {
            predicates.add(criteriaBuilder.equal(root.get("group").get("uuid"), groupUuid));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static SearchAssignmentExceptionSpecification fromSearchRequest(SearchAssignmentExceptionRequest searchAssignmentExceptionRequest) {
        return builder()
                .userUuid(searchAssignmentExceptionRequest.getUserUuid())
                .groupUuid(searchAssignmentExceptionRequest.getGroupUuid())
                .build();
    }
}
