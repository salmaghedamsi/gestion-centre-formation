package com.centreformation.CentreFormationBackend.entity;

import com.centreformation.CentreFormationBackend.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formation_groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String Subject;

    @Column(nullable = false)
    private Double Price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @JsonIgnore
    @OneToMany(mappedBy = "formationGroup", cascade = CascadeType.ALL)
    private List<Group> groups = new ArrayList<>();
}