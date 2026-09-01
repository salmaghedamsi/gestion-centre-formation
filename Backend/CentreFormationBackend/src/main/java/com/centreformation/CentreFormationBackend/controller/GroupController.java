package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.*;
import com.centreformation.CentreFormationBackend.dto.GroupCreationDTO;
import com.centreformation.CentreFormationBackend.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private GroupStudentService groupStudentService;

    @Autowired
    private FormationGroupService formationGroupService;

    @GetMapping
    public java.util.List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    public Group addGroup(@Valid @RequestBody GroupCreationDTO dto) {

        Enseignant enseignant =
                enseignantService.findById(dto.getEnseignantId());

        FormationGroup formationGroup =
                formationGroupService.findById(dto.getFormationGroupId());

        Group group = new Group();

        group.setName(genererNomGroupe(formationGroup, dto.getStartDate()));
        group.setStartDate(dto.getStartDate());
        group.setEndDate(dto.getEndDate());
        group.setMaxPlaces(dto.getMaxPlaces());
        group.setTeacher(enseignant);
        group.setFormationGroup(formationGroup);
        group.setPaymentType(formationGroup.getPaymentType());

        return groupService.CreateGroup(group);
    }
    @GetMapping("/{groupId}/students")
    public List<GroupStudent> getStudentsByGroup(@PathVariable Long groupId) {
        return groupStudentService.findByGroupId(groupId);
    }
    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id,
                             @Valid @RequestBody GroupCreationDTO dto) {

        Enseignant enseignant =
                enseignantService.findById(dto.getEnseignantId());

        FormationGroup formationGroup =
                formationGroupService.findById(dto.getFormationGroupId());

        Group group = new Group();

        group.setName(genererNomGroupe(formationGroup, dto.getStartDate()));
        group.setStartDate(dto.getStartDate());
        group.setEndDate(dto.getEndDate());
        group.setMaxPlaces(dto.getMaxPlaces());
        group.setTeacher(enseignant);
        group.setFormationGroup(formationGroup);
        group.setPaymentType(formationGroup.getPaymentType());

        return groupService.updateGroup(id, group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{groupId}/students/{studentId}")
    public GroupStudent createStudent(
            @PathVariable Long groupId,
            @PathVariable Long studentId) {

        Group group = groupService.getGroupById(groupId);
        Student student = studentService.findById(studentId);

        int inscritsActifs = groupStudentService.findActiveByGroupId(groupId).size();

        try {
            GroupStudent groupStudent =
                    groupStudentService.findByGroupAndStudent(groupId, studentId);

            if (!groupStudent.isActive() && inscritsActifs >= group.getMaxPlaces()) {
                throw new IllegalStateException("Le groupe a atteint sa capacité maximale (" + group.getMaxPlaces() + " places).");
            }

            groupStudent.setActive(true);
            groupStudent.setStartDate(LocalDate.now());
            groupStudent.setEndDate(null);

            return groupStudentService.save(groupStudent);

        } catch (EntityNotFoundException e) {

            if (inscritsActifs >= group.getMaxPlaces()) {
                throw new IllegalStateException("Le groupe a atteint sa capacité maximale (" + group.getMaxPlaces() + " places).");
            }

            GroupStudent groupStudent = new GroupStudent();

            groupStudent.setGroup(group);
            groupStudent.setStudent(student);
            groupStudent.setStartDate(LocalDate.now());
            groupStudent.setActive(true);

            return groupStudentService.save(groupStudent);
        }
    }

    @DeleteMapping("/{groupId}/students/{studentId}")
    public GroupStudent DeleteStudent(@PathVariable Long groupId, @PathVariable Long studentId) {
        GroupStudent groupStudent = groupStudentService.findByGroupAndStudent(groupId, studentId);

        groupStudent.setActive(false);
        groupStudent.setEndDate(LocalDate.now());

        return groupStudentService.save(groupStudent);
    }
    private String genererNomGroupe(FormationGroup formationGroup, LocalDateTime startDate) {

        String mois = startDate.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.FRENCH);

        String moisCapitalise =
                mois.substring(0, 1).toUpperCase() + mois.substring(1);

        return formationGroup.getSubject()
                + " - "
                + moisCapitalise
                + " "
                + startDate.getYear();
    }

}