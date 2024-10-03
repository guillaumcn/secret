package com.guillaumcn.secretsanta.repository.specification;

import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
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
public class SearchGroupSpecification implements Specification<GroupEntity> {

    private String name;
    private String ownerUuid;

    @Override
    public Predicate toPredicate(Root<GroupEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
        }

        if (Objects.nonNull(ownerUuid)) {
            predicates.add(criteriaBuilder.equal(root.get("owner").get("uuid"), ownerUuid));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static SearchGroupSpecification fromSearchRequest(SearchGroupRequest searchGroupRequest) {
        return SearchGroupSpecification.builder()
                .name(searchGroupRequest.getName())
                .ownerUuid(searchGroupRequest.getOwnerUuid())
                .build();
    }
}
