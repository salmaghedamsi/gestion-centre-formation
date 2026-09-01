package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.Student;

import java.util.List;

public interface StudentService {

    Student findById(Long id);

    List<Student> findAll();

    Student save(Student student);

    void deleteById(Long id);

}