package com.centreformation.CentreFormationBackend.controller;

import com.centreformation.CentreFormationBackend.dto.PresenceDTO;
import com.centreformation.CentreFormationBackend.dto.PresenceRequestDTO;
import com.centreformation.CentreFormationBackend.entity.Presence;
import com.centreformation.CentreFormationBackend.entity.Session;
import com.centreformation.CentreFormationBackend.entity.Student;
import com.centreformation.CentreFormationBackend.service.PresenceService;
import com.centreformation.CentreFormationBackend.service.SessionService;
import com.centreformation.CentreFormationBackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/session/{sessionId}")
    public List<PresenceDTO> getBySession(
            @PathVariable Long sessionId
    ) {
        return presenceService.getPresencesBySession(sessionId);
    }
    @PutMapping("/session/{sessionId}")
    public List<PresenceDTO> savePresences(
            @PathVariable Long sessionId,
            @RequestBody List<PresenceRequestDTO> requests
    ) {
        return presenceService.savePresences(
                sessionId,
                requests
        );
    }






}