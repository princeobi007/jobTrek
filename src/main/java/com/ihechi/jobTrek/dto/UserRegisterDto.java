package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ihechi.jobTrek.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterDto (
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email") String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @JsonProperty("password") String password,

    @NotNull(message = "Role is required")
    @JsonProperty("role") UserRole role){
}
