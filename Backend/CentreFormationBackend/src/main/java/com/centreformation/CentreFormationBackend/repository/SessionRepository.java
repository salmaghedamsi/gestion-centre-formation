package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByGroupId(Long groupId);

    List<Session> findByGroupIdAndFreeFalse(Long groupId);

    List<Session> findByGroupIdAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Session> findByGroupIdAndFreeFalseAndDateBetween(
            Long groupId,
            LocalDate startDate,
            LocalDate endDate
    );
}