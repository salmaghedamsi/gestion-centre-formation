package com.centreformation.CentreFormationBackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
public class PaymentCreationDTO {
    private Long groupStudentId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private Integer monthsPaid;
    private Integer sessionsPaid;
    private String comment;
}
