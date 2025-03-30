package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ihechi.jobTrek.enums.UserRole;

public record UserResponseDto (
    @JsonProperty("email") String email,
    @JsonProperty("role") UserRole role
){}
