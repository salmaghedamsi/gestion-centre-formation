package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.Enseignant;

import java.util.List;

public interface EnseignantService {

    Enseignant findById(Long id);

    List<Enseignant> findAll();

    Enseignant save(Enseignant enseignant);

    void deleteById(Long id);

}