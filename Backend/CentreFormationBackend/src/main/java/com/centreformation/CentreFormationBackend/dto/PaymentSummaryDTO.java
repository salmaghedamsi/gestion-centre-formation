package com.centreformation.CentreFormationBackend.dto;

import com.centreformation.CentreFormationBackend.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentSummaryDTO {
    private PaymentType paymentType;
    private Double pricePerUnit;
    private BigDecimal totalAmountPaid;
    private int unitsPaid;
    private int unitsConsumed;
    private int balanceUnits;
}