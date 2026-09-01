package com.centreformation.CentreFormationBackend.dto;

import com.centreformation.CentreFormationBackend.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class GroupCreationDTO {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int maxPlaces;

        private Long enseignantId;
        private Long formationGroupId;
        private PaymentType paymentType;
    }
