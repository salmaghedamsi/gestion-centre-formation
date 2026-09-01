package com.centreformation.CentreFormationBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDTO {

    private Long studentId;
    private String firstName;
    private String lastName;
    private boolean present;
}