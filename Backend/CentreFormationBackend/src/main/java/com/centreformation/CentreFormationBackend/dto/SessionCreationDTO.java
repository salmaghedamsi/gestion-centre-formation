package com.centreformation.CentreFormationBackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SessionCreationDTO {

    private Long groupId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean free;
}