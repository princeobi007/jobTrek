package com.ihechi.jobTrek.repository;

import com.ihechi.jobTrek.domain.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
  Page<JobEntity> findAll(Pageable pageable); // Supports pagination
  Page<JobEntity> findByPostedById(Long companyId, Pageable pageable); // G
}
