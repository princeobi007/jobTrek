package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ApplicationCreateDto(
    @NotNull(message = "Job ID is required")
    @JsonProperty("jobId") Long jobId,

    @NotBlank(message = "Cover letter is required")
    @Size(max = 1000, message = "Cover letter cannot exceed 1000 characters")
    @JsonProperty("coverLetter") String coverLetter
) {

}
