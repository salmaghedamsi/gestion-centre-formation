package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.FormationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationGroupRepository extends JpaRepository<FormationGroup, Long> {
}