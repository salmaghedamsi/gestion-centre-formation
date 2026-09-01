package com.centreformation.CentreFormationBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Group registration associated with this payment.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_student_id", nullable = false)
    private GroupStudent groupStudent;

    /**
     * Date when the payment was made.
     */
    @Column(nullable = false)
    private LocalDate paymentDate;

    /**
     * Amount paid.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * Number of months covered by this payment.
     *
     * Used when the group's payment type is MONTHLY.
     */
    private Integer monthsPaid;

    /**
     * Number of sessions covered by this payment.
     *
     * Used when the group's payment type is PER_SESSION.
     */
    private Integer sessionsPaid;

    /**
     * Optional comment about the payment.
     */
    private String comment;
}