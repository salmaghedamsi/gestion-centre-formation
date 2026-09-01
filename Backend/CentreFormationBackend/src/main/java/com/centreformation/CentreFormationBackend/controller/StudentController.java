package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.Student;
import com.centreformation.CentreFormationBackend.dto.StudentCreationDTO;
import com.centreformation.CentreFormationBackend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    public Student createStudent(@Valid @RequestBody StudentCreationDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPhone(dto.getPhone());
        student.setBirthDate(dto.getBirthDate());
        student.setLevel(dto.getLevel());

        return studentService.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody StudentCreationDTO dto) {
        Student student = studentService.findById(id);

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPhone(dto.getPhone());
        student.setBirthDate(dto.getBirthDate());
        student.setLevel(dto.getLevel());

        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}