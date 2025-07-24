package com.tasks.dblayer.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank String userName,
        @NotBlank String fullName,
        String location,
        String bio,
        @NotBlank String email,
        @NotBlank @Size(min = 8) String password
        ) { 
    // This record is used to encapsulate the data for a sign-up request.
}

