package com.guillaumcn.secretsanta.controller;

import com.guillaumcn.secretsanta.domain.exception.GroupNotFoundException;
import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.request.group.AssignGroupUserRequest;
import com.guillaumcn.secretsanta.domain.request.group.CreateGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.SearchGroupRequest;
import com.guillaumcn.secretsanta.domain.request.group.UpdateGroupRequest;
import com.guillaumcn.secretsanta.domain.response.group.CreateGroupResponse;
import com.guillaumcn.secretsanta.domain.response.group.GetGroupResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import com.guillaumcn.secretsanta.service.group.GroupService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public CreateGroupResponse createGroup(@RequestBody @Valid CreateGroupRequest createGroupRequest) throws UserNotFoundException {
        return groupService.createGroup(createGroupRequest);
    }

    @GetMapping
    public List<GetGroupResponse> searchGroups(@Valid @Nullable SearchGroupRequest searchGroupRequest) {
        return groupService.searchGroups(searchGroupRequest);
    }

    @GetMapping("/{group_uuid}")
    public GetGroupResponse getGroup(@PathVariable(name = "group_uuid") String uuid) throws GroupNotFoundException {
        return groupService.getGroup(uuid);
    }

    @DeleteMapping("/{group_uuid}")
    public void deleteGroup(@PathVariable(name = "group_uuid") String uuid) throws GroupNotFoundException {
        groupService.deleteGroup(uuid);
    }

    @PatchMapping("/{group_uuid}")
    public void patchGroup(@PathVariable(name = "group_uuid") String uuid, @RequestBody @Valid UpdateGroupRequest updateGroupRequest) throws GroupNotFoundException {
        groupService.updateGroup(uuid, updateGroupRequest);
    }

    @PostMapping("/{group_uuid}/users")
    public void assignUserToGroup(@PathVariable(name = "group_uuid") String uuid, @RequestBody @Valid AssignGroupUserRequest assignGroupUserRequest) throws UserNotFoundException, GroupNotFoundException {
        groupService.assignUserToGroup(uuid, assignGroupUserRequest);
    }

    @GetMapping("/{group_uuid}/users")
    public List<GetUserResponse> getGroupUsers(@PathVariable(name = "group_uuid") String uuid) throws GroupNotFoundException {
        return groupService.getGroupUsers(uuid);
    }
}
