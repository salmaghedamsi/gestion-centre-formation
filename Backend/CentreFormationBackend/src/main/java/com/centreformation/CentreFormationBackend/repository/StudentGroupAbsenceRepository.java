package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.StudentGroupAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StudentGroupAbsenceRepository extends JpaRepository<StudentGroupAbsence, Long> {

    List<StudentGroupAbsence> findByGroupStudentId(
            Long groupStudentId
    );

    List<StudentGroupAbsence> findByGroupStudentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long groupStudentId,
            LocalDate date,
            LocalDate sameDate
    );
}