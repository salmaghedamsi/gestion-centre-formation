package com.centreformation.CentreFormationBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(
        name = "presences",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_session_student",
                        columnNames = {"session_id", "student_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_presence_student",
                        columnList = "student_id"
                ),
                @Index(
                        name = "idx_presence_session",
                        columnList = "session_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Session associated with this attendance record.
     */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonIgnore
    private Session session;

    /**
     * Student associated with this attendance record.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * Indicates whether the student was present.
     *
     * true  = present
     * false = absent
     */
    @Column(nullable = false)
    private boolean present;
}