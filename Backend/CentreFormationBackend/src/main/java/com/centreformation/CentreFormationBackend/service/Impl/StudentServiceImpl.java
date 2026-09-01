package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.Student;
import com.centreformation.CentreFormationBackend.repository.GroupStudentRepository;
import com.centreformation.CentreFormationBackend.repository.StudentRepository;
import com.centreformation.CentreFormationBackend.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupStudentRepository groupStudentRepository;
    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Student not found with id: " + id
                        )
                );
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Student not found with id: " + id
            );
        }

        studentRepository.deleteById(id);
    }
}

