package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;

import java.util.List;

public interface GroupStudentService {

    GroupStudent findById(Long id);

    GroupStudent findByGroupAndStudent(Long groupId, Long studentId);

    List<GroupStudent> findAll();

    List<GroupStudent> findByGroupId(Long groupId);

    List<GroupStudent> findByStudentId(Long studentId);

    List<GroupStudent> findActiveByGroupId(Long groupId);

    List<GroupStudent> findActiveByStudentId(Long studentId);

    GroupStudent save(GroupStudent groupStudent);

    void deleteById(Long id);

}