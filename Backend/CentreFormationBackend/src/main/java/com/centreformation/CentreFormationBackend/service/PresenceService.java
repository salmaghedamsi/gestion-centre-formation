package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.dto.PresenceDTO;
import com.centreformation.CentreFormationBackend.dto.PresenceRequestDTO;
import com.centreformation.CentreFormationBackend.entity.Presence;

import java.util.List;

public interface PresenceService {

    Presence findById(Long id);

    Presence findBySessionAndStudent(
            Long sessionId,
            Long studentId
    );

    List<Presence> findAll();


    List<Presence> findByStudentId(Long studentId);

    List<Presence> findPresentByStudentId(Long studentId);

    long countPresentBySessionId(Long sessionId);

    long countPresentByStudentAndGroup(
            Long studentId,
            Long groupId
    );

    Presence save(Presence presence);
    List<Presence> findPresentByStudentAndGroup(Long studentId, Long groupId);


    void deleteById(Long id);
    List<PresenceDTO> getPresencesBySession(Long sessionId);
    List<PresenceDTO> savePresences(
            Long sessionId,
            List<PresenceRequestDTO> requests
    );}