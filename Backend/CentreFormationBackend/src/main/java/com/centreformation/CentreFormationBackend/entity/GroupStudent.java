package com.centreformation.CentreFormationBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "group_students",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_group_student",
                        columnNames = {"group_id", "student_id"}
                )
        }
)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Group in which the student is registered.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    /**
     * Registered student.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * Date when the student joined the group.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * Date when the student left the group.
     * Null means that the student is still registered.
     */
    private LocalDate endDate;

    /**
     * Indicates whether the registration is currently active.
     */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * Periods during which the student is officially absent
     * from this group and should not be charged.
     */
    @JsonIgnore
    @OneToMany(
            mappedBy = "groupStudent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<StudentGroupAbsence> absences = new ArrayList<>();

    /**
     * Payments made for this group registration.
     */
    @OneToMany(
            mappedBy = "groupStudent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Payment> payments = new ArrayList<>();
}