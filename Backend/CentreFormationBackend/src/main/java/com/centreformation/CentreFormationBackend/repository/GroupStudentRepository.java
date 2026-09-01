package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

    Optional<GroupStudent> findByGroupIdAndStudentId(Long groupId, Long studentId);

    List<GroupStudent> findByGroupId(Long groupId);

    List<GroupStudent> findByStudentId(Long studentId);

    List<GroupStudent> findByGroupIdAndActiveTrue(Long groupId);

    List<GroupStudent> findByStudentIdAndActiveTrue(Long studentId);
    long countByGroupIdAndActiveTrue(Long groupId);
}