package com.ihechi.jobTrek.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record ApplicationResponseDto(
    @JsonProperty("id") Long id,
    @JsonProperty("jobId") Long jobId,
    @JsonProperty("applicantId") Long applicantId,
    @JsonProperty("coverLetter") String coverLetter,
    @JsonProperty("appliedAt") LocalDateTime appliedAt
) {

}
