package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.entity.Session;
import com.centreformation.CentreFormationBackend.repository.SessionRepository;
import com.centreformation.CentreFormationBackend.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Session findById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Session not found with id: " + id
                        )
                );
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public List<Session> findByGroupId(Long groupId) {
        return sessionRepository.findByGroupId(groupId);
    }

    @Override
    public List<Session> findBillableSessionsByGroupId(Long groupId) {
        return sessionRepository.findByGroupIdAndFreeFalse(groupId);
    }

    @Override
    public List<Session> findByGroupIdAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return sessionRepository.findByGroupIdAndDateBetween(
                groupId,
                startDate,
                endDate
        );
    }

    @Override
    public List<Session> findBillableSessionsByGroupIdAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return sessionRepository
                .findByGroupIdAndFreeFalseAndDateBetween(
                        groupId,
                        startDate,
                        endDate
                );
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteById(Long id) {
        if (!sessionRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Session not found with id: " + id
            );
        }

        sessionRepository.deleteById(id);
    }

}