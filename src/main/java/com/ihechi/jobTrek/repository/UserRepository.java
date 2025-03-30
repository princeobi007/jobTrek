package com.ihechi.jobTrek.repository;

import com.ihechi.jobTrek.domain.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}
