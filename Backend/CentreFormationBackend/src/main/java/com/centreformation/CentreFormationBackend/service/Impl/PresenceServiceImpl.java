package com.centreformation.CentreFormationBackend.service.Impl;

import com.centreformation.CentreFormationBackend.dto.PresenceDTO;
import com.centreformation.CentreFormationBackend.dto.PresenceRequestDTO;
import com.centreformation.CentreFormationBackend.entity.GroupStudent;
import com.centreformation.CentreFormationBackend.entity.Presence;
import com.centreformation.CentreFormationBackend.entity.Session;
import com.centreformation.CentreFormationBackend.entity.Student;
import com.centreformation.CentreFormationBackend.repository.PresenceRepository;
import com.centreformation.CentreFormationBackend.repository.SessionRepository;
import com.centreformation.CentreFormationBackend.service.PresenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresenceServiceImpl implements PresenceService {

    private final PresenceRepository presenceRepository;
    private final SessionRepository sessionRepository;

    // ==============================
    // Méthodes existantes
    // ==============================

    @Override
    public Presence findById(Long id) {
        return presenceRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Presence not found with id: " + id
                        )
                );
    }
    @Override
    public List<Presence> findPresentByStudentAndGroup(
            Long studentId,
            Long groupId
    ) {
        return presenceRepository.findByStudentIdAndPresentTrue(studentId).stream()
                .filter(p -> p.getSession().getGroup().getId().equals(groupId))
                .filter(p -> !p.getSession().isFree())
                .toList();
    }
    @Override
    public Presence findBySessionAndStudent(
            Long sessionId,
            Long studentId
    ) {
        return presenceRepository
                .findBySessionIdAndStudentId(sessionId, studentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Presence not found for this session and student."
                        )
                );
    }

    @Override
    public List<Presence> findAll() {
        return presenceRepository.findAll();
    }

    @Override
    public List<Presence> findByStudentId(Long studentId) {
        return presenceRepository.findByStudentId(studentId);
    }

    @Override
    public List<Presence> findPresentByStudentId(Long studentId) {
        return presenceRepository.findByStudentIdAndPresentTrue(studentId);
    }

    @Override
    public long countPresentBySessionId(Long sessionId) {
        return presenceRepository
                .countBySessionIdAndPresentTrue(sessionId);
    }

    @Override
    public long countPresentByStudentAndGroup(
            Long studentId,
            Long groupId
    ) {
        return presenceRepository
                .countByStudentIdAndSessionGroupIdAndPresentTrueAndSessionFreeFalse(
                        studentId,
                        groupId
                );
    }

    @Override
    public Presence save(Presence presence) {
        return presenceRepository.save(presence);
    }

    @Override
    public void deleteById(Long id) {

        if (!presenceRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Presence not found with id: " + id
            );
        }

        presenceRepository.deleteById(id);
    }

    // ==============================
    // Récupérer les élèves d'une session
    // ==============================

    @Override
    public List<PresenceDTO> getPresencesBySession(Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Session not found"
                        )
                );

        List<Presence> presencesExistantes =
                presenceRepository.findBySessionId(sessionId);

        List<PresenceDTO> resultat = new ArrayList<>();

        for (GroupStudent groupStudent :
                session.getGroup().getGroupStudents()) {

            // Seulement les étudiants actifs
            if (!groupStudent.isActive()) {
                continue;
            }

            Student student = groupStudent.getStudent();

            Optional<Presence> presenceExistante =
                    presencesExistantes.stream()
                            .filter(p ->
                                    p.getStudent()
                                            .getId()
                                            .equals(student.getId())
                            )
                            .findFirst();

            boolean present = presenceExistante
                    .map(Presence::isPresent)
                    .orElse(false);

            resultat.add(
                    new PresenceDTO(
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            present
                    )
            );
        }

        return resultat;
    }

    // ==============================
    // Enregistrer toute la liste
    // ==============================

    @Override
    @Transactional
    public List<PresenceDTO> savePresences(
            Long sessionId,
            List<PresenceRequestDTO> requests
    ) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Session not found"
                        )
                );

        for (PresenceRequestDTO request : requests) {

            Long studentId = request.getStudentId();

            GroupStudent groupStudent =
                    session.getGroup()
                            .getGroupStudents()
                            .stream()
                            .filter(gs ->
                                    gs.getStudent()
                                            .getId()
                                            .equals(studentId)
                                            && gs.isActive()
                            )
                            .findFirst()
                            .orElseThrow(() ->
                                    new EntityNotFoundException(
                                            "Student is not registered in this group"
                                    )
                            );

            Presence presence =
                    presenceRepository
                            .findBySessionIdAndStudentId(
                                    sessionId,
                                    studentId
                            )
                            .orElseGet(() -> {

                                Presence newPresence =
                                        new Presence();

                                newPresence.setSession(session);
                                newPresence.setStudent(
                                        groupStudent.getStudent()
                                );

                                return newPresence;
                            });

            presence.setPresent(request.isPresent());

            presenceRepository.save(presence);
        }

        return getPresencesBySession(sessionId);
    }

}