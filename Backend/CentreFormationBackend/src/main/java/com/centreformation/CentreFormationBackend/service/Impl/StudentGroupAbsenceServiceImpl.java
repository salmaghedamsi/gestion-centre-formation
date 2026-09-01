package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.StudentGroupAbsence;
import com.centreformation.CentreFormationBackend.repository.StudentGroupAbsenceRepository;
import com.centreformation.CentreFormationBackend.service.StudentGroupAbsenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentGroupAbsenceServiceImpl
        implements StudentGroupAbsenceService {

    private final StudentGroupAbsenceRepository absenceRepository;

    @Override
    public StudentGroupAbsence findById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student group absence not found with id: " + id
                        )
                );
    }

    @Override
    public List<StudentGroupAbsence> findAll() {
        return absenceRepository.findAll();
    }

    @Override
    public List<StudentGroupAbsence> findByGroupStudentId(
            Long groupStudentId
    ) {
        return absenceRepository.findByGroupStudentId(
                groupStudentId
        );
    }

    @Override
    public boolean isAbsent(
            Long groupStudentId,
            LocalDate date
    ) {
        return !absenceRepository
                .findByGroupStudentIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        groupStudentId,
                        date,
                        date
                )
                .isEmpty();
    }

    @Override
    public StudentGroupAbsence save(
            StudentGroupAbsence absence
    ) {
        return absenceRepository.save(absence);
    }

    @Override
    public void deleteById(Long id) {
        if (!absenceRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Student group absence not found with id: " + id
            );
        }

        absenceRepository.deleteById(id);
    }
}