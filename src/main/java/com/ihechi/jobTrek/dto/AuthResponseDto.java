package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponseDto(
    @JsonProperty("token") String token,
    @JsonProperty("user") UserResponseDto user
) {

}
