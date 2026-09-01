package com.centreformation.CentreFormationBackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class StudentCreationDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String phone;

    private Date birthDate;

    private String level;
}
