package com.centreformation.CentreFormationBackend.entity;

import com.centreformation.CentreFormationBackend.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Group name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Payment method used for this group.
     *
     * MONTHLY     -> students pay on a monthly basis.
     * PER_SESSION -> students pay according to the number of sessions.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    /**
     * Students registered in this group.
     *
     * GroupStudent is used instead of a direct ManyToMany relationship
     * because the registration itself contains business information
     * such as start date, end date and active status.
     */
    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<GroupStudent> groupStudents = new ArrayList<>();

    /**
     * Sessions organized for this group.
     */
    public int getActiveStudentsCount() {
        if (groupStudents == null) {
            return 0;
        }
        return (int) groupStudents.stream()
                .filter(GroupStudent::isActive)
                .count();
    }

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Session> sessions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Enseignant teacher;

    @ManyToOne
    @JoinColumn(name = "formation_group_id", nullable = false)
    private FormationGroup formationGroup;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private int maxPlaces;

}