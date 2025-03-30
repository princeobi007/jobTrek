package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record JobResponseDto(
    @JsonProperty("id") Long id,
    @JsonProperty("title") String title,
    @JsonProperty("description") String description,
    @JsonProperty("companyName") String companyName,
    @JsonProperty("location") String location,
    @JsonProperty("jobType") String jobType,
    @JsonProperty("postedAt") LocalDateTime postedAt
) {

}
