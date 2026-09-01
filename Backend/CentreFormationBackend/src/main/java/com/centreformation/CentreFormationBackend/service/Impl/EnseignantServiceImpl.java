package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.Enseignant;
import com.centreformation.CentreFormationBackend.entity.Group;
import com.centreformation.CentreFormationBackend.repository.EnseignantRepository;
import com.centreformation.CentreFormationBackend.repository.StudentRepository;
import com.centreformation.CentreFormationBackend.service.EnseignantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.centreformation.CentreFormationBackend.repository.GroupRepository;
import java.util.List;
import com.centreformation.CentreFormationBackend.repository.GroupStudentRepository;
@Service
@RequiredArgsConstructor
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final GroupStudentRepository groupStudentRepository;

    @Override
    public Enseignant findById(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant not found with id: " + id));
    }

    @Override
    public List<Enseignant> findAll() {
        return enseignantRepository.findAll();
    }

    @Override
    public Enseignant save(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @Override
    public void deleteById(Long id) {
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant not found with id: " + id);
        }

        List<Group> groupes = groupRepository.findByTeacherId(id);
        if (!groupes.isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer cet enseignant : il est encore assigné à "
                            + groupes.size() + " groupe(s). Veuillez réassigner ou retirer ces groupes d'abord."
            );
        }

        enseignantRepository.deleteById(id);
    }



}