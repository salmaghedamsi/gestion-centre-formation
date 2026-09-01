package com.centreformation.CentreFormationBackend.entity;

import com.centreformation.CentreFormationBackend.entity.Presence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Group for which the session is organized.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Date of the session.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * Start time of the session.
     */
    private LocalTime startTime;

    /**
     * End time of the session.
     */
    private LocalTime endTime;

    /**
     * Indicates whether the session is free.
     *
     * Free sessions are excluded from payment calculations.
     */
    @Column(nullable = false)
    private boolean free = false;

    /**
     * Attendance records for this session.
     */
    @JsonIgnore
    @OneToMany(
            mappedBy = "session",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Presence> presences = new ArrayList<>();
}