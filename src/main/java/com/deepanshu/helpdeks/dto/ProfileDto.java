package com.deepanshu.helpdeks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfileDto {

    @NotBlank(message = "Full Name cannot be empty")
    private String fullName;


    private String bio;

    private String experience;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @NotBlank(message = "Linkedin Profile cannot be empty")
    private String linkedinProfile;

    private String skills;
}
