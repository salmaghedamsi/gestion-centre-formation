package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.service.GroupStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group-students")
public class GroupStudentController {

    @Autowired
    private GroupStudentService groupStudentService;

    @GetMapping("/{id}")
    public GroupStudent getById(@PathVariable Long id) {
        return groupStudentService.findById(id);
    }
}