package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobCreateDto(
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @JsonProperty("title") String title,

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @JsonProperty("description") String description,

    @NotBlank(message = "Company name is required")
    @JsonProperty("companyName") String companyName,

    @NotBlank(message = "Location is required")
    @JsonProperty("location") String location,

    @NotBlank(message = "Job type is required")
    @JsonProperty("jobType") String jobType
) {

}
