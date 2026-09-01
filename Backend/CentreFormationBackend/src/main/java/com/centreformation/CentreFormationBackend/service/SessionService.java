package com.centreformation.CentreFormationBackend.service;

import com.centreformation.CentreFormationBackend.entity.Session;

import java.time.LocalDate;
import java.util.List;

public interface SessionService {

    Session findById(Long id);

    List<Session> findAll();

    List<Session> findByGroupId(Long groupId);

    List<Session> findBillableSessionsByGroupId(Long groupId);

    List<Session> findByGroupIdAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Session> findBillableSessionsByGroupIdAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    );

    Session save(Session session);

    void deleteById(Long id);
}