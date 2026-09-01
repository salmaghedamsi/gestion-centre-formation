package com.centreformation.CentreFormationBackend.repository;

import com.centreformation.CentreFormationBackend.entity.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    Optional<Presence> findBySessionIdAndStudentId(Long sessionId, Long studentId);

    List<Presence> findBySessionId(Long sessionId);
    List<Presence> findByStudentId(Long studentId);

    List<Presence> findByStudentIdAndPresentTrue(Long studentId);

    long countBySessionIdAndPresentTrue(Long sessionId);

    long countByStudentIdAndPresentTrue(Long studentId);

    long countByStudentIdAndSessionGroupIdAndPresentTrueAndSessionFreeFalse(
            Long studentId,
            Long groupId
    );

}