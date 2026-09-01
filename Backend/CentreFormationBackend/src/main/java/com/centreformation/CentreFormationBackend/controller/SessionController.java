package com.centreformation.CentreFormationBackend.controller;
import com.centreformation.CentreFormationBackend.dto.SessionCreationDTO;
import com.centreformation.CentreFormationBackend.entity.Group;
import com.centreformation.CentreFormationBackend.entity.Session;
import com.centreformation.CentreFormationBackend.service.GroupService;
import com.centreformation.CentreFormationBackend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Session> getAll() {
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    public Session getById(@PathVariable Long id) {
        return sessionService.findById(id);
    }

    @GetMapping("/group/{groupId}")
    public List<Session> getByGroup(@PathVariable Long groupId) {
        return sessionService.findByGroupId(groupId);
    }

    @PostMapping
    public Session CreateSession(@RequestBody SessionCreationDTO dto) {

        Group group = groupService.getGroupById(dto.getGroupId());

        LocalDate debutGroupe = group.getStartDate().toLocalDate();
        LocalDate finGroupe = group.getEndDate().toLocalDate();

        if (dto.getDate().isBefore(debutGroupe) || dto.getDate().isAfter(finGroupe)) {
            throw new IllegalStateException(
                    "La date de la séance doit être comprise entre le "
                            + debutGroupe + " et le " + finGroupe + " (dates du groupe)."
            );
        }

        if (dto.getStartTime() != null && dto.getEndTime() != null
                && !dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new IllegalStateException(
                    "L'heure de fin doit être après l'heure de début."
            );
        }

        Session session = new Session();

        session.setGroup(group);
        session.setDate(dto.getDate());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setFree(dto.isFree());

        return sessionService.save(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sessionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}