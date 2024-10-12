package com.guillaumcn.secretsanta.repository.specification;

import com.guillaumcn.secretsanta.domain.model.AssignmentEntity;
import com.guillaumcn.secretsanta.domain.request.assignment.SearchAssignmentRequest;
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
public class SearchAssignmentSpecification implements Specification<AssignmentEntity> {

    private String groupUuid;
    private String sourceUserUuid;

    @Override
    public Predicate toPredicate(Root<AssignmentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(sourceUserUuid)) {
            predicates.add(criteriaBuilder.equal(root.get("sourceUser").get("uuid"), sourceUserUuid));
        }

        if (Objects.nonNull(groupUuid)) {
            predicates.add(criteriaBuilder.equal(root.get("group").get("uuid"), groupUuid));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static SearchAssignmentSpecification fromSearchRequest(SearchAssignmentRequest searchAssignmentRequest) {
        return builder()
                .sourceUserUuid(searchAssignmentRequest.getSourceUserUuid())
                .groupUuid(searchAssignmentRequest.getGroupUuid())
                .build();
    }
}
