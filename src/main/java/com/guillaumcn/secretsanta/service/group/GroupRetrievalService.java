package com.guillaumcn.secretsanta.service.group;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
import com.guillaumcn.secretsanta.repository.GroupRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchGroupSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupRetrievalService {

    private final GroupRepository groupRepository;

    public GroupEntity findGroup(String groupUuid) throws GroupNotFoundException {
        return groupRepository.findById(groupUuid).orElseThrow(() -> new GroupNotFoundException(groupUuid));
    }

    public List<GroupEntity> searchGroups(SearchGroupRequest searchGroupRequest) {
        return groupRepository.findAll(SearchGroupSpecification.fromSearchRequest(searchGroupRequest));
    }
}
