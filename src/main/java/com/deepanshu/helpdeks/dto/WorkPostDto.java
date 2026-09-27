package com.deepanshu.helpdeks.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter

public class WorkPostDto {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Title cannot be blank")
    private String description;

    private String skillsRequired;

    @NotBlank(message = "Maximum Pay cannot be blank")
    private String maximumPay;

    @NotBlank(message = "Minimum Pay cannot be blank")
    private String minimumPay;

    private String filepath;

}
