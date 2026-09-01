package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
