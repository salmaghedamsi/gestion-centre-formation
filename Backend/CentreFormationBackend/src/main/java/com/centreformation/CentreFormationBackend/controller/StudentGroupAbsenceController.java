package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.entity.StudentGroupAbsence;
import com.centreformation.CentreFormationBackend.service.GroupStudentService;
import com.centreformation.CentreFormationBackend.service.StudentGroupAbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
public class StudentGroupAbsenceController {

    @Autowired
    private StudentGroupAbsenceService absenceService;

    @Autowired
    private GroupStudentService groupStudentService;

    @GetMapping("/group-student/{groupStudentId}")
    public List<StudentGroupAbsence> getByGroupStudent(@PathVariable Long groupStudentId) {
        return absenceService.findByGroupStudentId(groupStudentId);
    }

    @PostMapping
    public StudentGroupAbsence CreateStudentGroupAbs(@RequestBody StudentGroupAbsence absence) {
        GroupStudent groupStudent = groupStudentService.findById(absence.getGroupStudent().getId());
        absence.setGroupStudent(groupStudent);

        return absenceService.save(absence);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        absenceService.deleteById(id);
    }
}