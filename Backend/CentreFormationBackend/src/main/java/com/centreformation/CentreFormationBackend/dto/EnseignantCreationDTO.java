package com.centreformation.CentreFormationBackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnseignantCreationDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String phone;
    private String email;
    private String speciality;
}
