package com.ihechi.jobTrek.repository;

import com.ihechi.jobTrek.domain.ApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
  Page<ApplicationEntity> findByJobId( Long jobId, Pageable pageable); // Get applications for a job
  Page<ApplicationEntity> findByApplicantId(Long applicantId, Pageable pageable); // Get applications by a developer
}
