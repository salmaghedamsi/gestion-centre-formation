package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.FormationGroup;
import com.centreformation.CentreFormationBackend.service.FormationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formation-groups")
public class FormationGroupController {

    @Autowired
    private FormationGroupService formationGroupService;

    @GetMapping
    public List<FormationGroup> getAll() {
        return formationGroupService.findAll();
    }

    @GetMapping("/{id}")
    public FormationGroup getById(@PathVariable Long id) {
        return formationGroupService.findById(id);
    }

    @PostMapping
    public FormationGroup CreateFormation(@RequestBody FormationGroup formationGroup) {
        return formationGroupService.save(formationGroup);
    }

    @PutMapping("/{id}")
    public FormationGroup Modify(@PathVariable Long id, @RequestBody FormationGroup formationGroupInput) {
        FormationGroup existing = formationGroupService.findById(id);

        existing.setSubject(formationGroupInput.getSubject());
        existing.setPrice(formationGroupInput.getPrice());
        existing.setPaymentType(formationGroupInput.getPaymentType());

        return formationGroupService.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        formationGroupService.deleteById(id);
    }
}