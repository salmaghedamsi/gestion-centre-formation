package com.centreformation.CentreFormationBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDTO {
    private Long StudentId;
    private String firstName;
    private String lastName;
    private double amount;
}
