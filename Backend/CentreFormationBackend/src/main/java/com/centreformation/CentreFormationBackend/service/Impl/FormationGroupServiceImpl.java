package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.FormationGroup;
import com.centreformation.CentreFormationBackend.repository.FormationGroupRepository;
import com.centreformation.CentreFormationBackend.service.FormationGroupService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormationGroupServiceImpl implements FormationGroupService {

    private final FormationGroupRepository formationGroupRepository;

    @Override
    public FormationGroup findById(Long id) {
        return formationGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FormationGroup non trouvée avec l'id " + id));
    }

    @Override
    public List<FormationGroup> findAll() {
        return formationGroupRepository.findAll();
    }

    @Override
    public FormationGroup save(FormationGroup formationGroup) {
        return formationGroupRepository.save(formationGroup);
    }

    @Override
    public void deleteById(Long id) {
        if (!formationGroupRepository.existsById(id)) {
            throw new EntityNotFoundException("FormationGroup non trouvée avec l'id " + id);
        }
        formationGroupRepository.deleteById(id);
    }

}