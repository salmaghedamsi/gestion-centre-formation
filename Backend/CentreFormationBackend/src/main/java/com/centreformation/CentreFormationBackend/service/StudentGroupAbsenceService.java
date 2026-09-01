package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.StudentGroupAbsence;

import java.time.LocalDate;
import java.util.List;

public interface StudentGroupAbsenceService {

    StudentGroupAbsence findById(Long id);

    List<StudentGroupAbsence> findAll();

    List<StudentGroupAbsence> findByGroupStudentId(
            Long groupStudentId
    );

    boolean isAbsent(
            Long groupStudentId,
            LocalDate date
    );

    StudentGroupAbsence save(
            StudentGroupAbsence absence
    );

    void deleteById(Long id);
}