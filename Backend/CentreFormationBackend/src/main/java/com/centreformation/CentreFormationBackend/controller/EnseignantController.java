package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.Enseignant;
import com.centreformation.CentreFormationBackend.dto.EnseignantCreationDTO;
import com.centreformation.CentreFormationBackend.service.EnseignantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantService.findAll();
    }

    @GetMapping("/{id}")
    public Enseignant getEnseignantById(@PathVariable Long id) {
        return enseignantService.findById(id);
    }

    @PostMapping
    public Enseignant CreateEnseignant(@Valid @RequestBody EnseignantCreationDTO dto) {
        Enseignant enseignant = new Enseignant();
        enseignant.setFirstName(dto.getFirstName());
        enseignant.setLastName(dto.getLastName());
        enseignant.setPhone(dto.getPhone());
        enseignant.setSpeciality(dto.getSpeciality());
        enseignant.setEmail(dto.getEmail());

        return enseignantService.save(enseignant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enseignantService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public Enseignant ModifyEnseignant(@PathVariable Long id, @Valid @RequestBody EnseignantCreationDTO dto) {
        Enseignant enseignant = enseignantService.findById(id);

        enseignant.setFirstName(dto.getFirstName());
        enseignant.setLastName(dto.getLastName());
        enseignant.setPhone(dto.getPhone());
        enseignant.setSpeciality(dto.getSpeciality());
        enseignant.setEmail(dto.getEmail());

        return enseignantService.save(enseignant);
    }
}