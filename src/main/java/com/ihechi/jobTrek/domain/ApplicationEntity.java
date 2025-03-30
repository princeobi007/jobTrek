package com.ihechi.jobTrek.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "applications")
public class ApplicationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "job_id", nullable = false)
  private JobEntity job; // The job being applied for

  @ManyToOne
  @JoinColumn(name = "applicant_id", nullable = false)
  private UserEntity applicant; // The developer applying

  @Column(nullable = false, length = 1000)
  private String coverLetter;

  @Column(nullable = false)
  private LocalDateTime appliedAt = LocalDateTime.now();

}
