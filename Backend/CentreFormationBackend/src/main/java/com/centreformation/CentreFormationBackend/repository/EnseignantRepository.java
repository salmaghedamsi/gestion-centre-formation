package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant,Long> {
}
