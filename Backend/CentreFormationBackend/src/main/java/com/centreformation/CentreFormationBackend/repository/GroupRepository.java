package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByTeacherId(Long teacherId);
}