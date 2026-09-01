package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.FormationGroup;

import java.util.List;

public interface FormationGroupService {

    FormationGroup findById(Long id);

    List<FormationGroup> findAll();

    FormationGroup save(FormationGroup formationGroup);

    void deleteById(Long id);

}