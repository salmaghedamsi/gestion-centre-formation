package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.entity.Group;

import java.util.List;

public interface GroupService {

    Group getGroupById(Long id);

    List<Group> getAllGroups();

    Group CreateGroup(Group groups);

    Group updateGroup(Long id, Group groups);

    void delete(Long id);
    List<GroupStudent> findByGroup_Id(Long groupId);
    long countActiveByGroupId(Long groupId);

}