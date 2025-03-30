package com.ihechi.jobTrek.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "jobs")
public class JobEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 500)
  private String description;

  @Column(nullable = false)
  private String companyName;

  @Column(nullable = false)
  private String location;

  @Column(nullable = false)
  private String jobType; // FULL_TIME, PART_TIME, CONTRACT

  @Column(nullable = false)
  private LocalDateTime postedAt = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "posted_by", nullable = false)
  private UserEntity postedBy; // The company that posted the job

  @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ApplicationEntity> applications; // List of applicants

}
