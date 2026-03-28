package com.reparto.repository;

import com.reparto.entity.WorkSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSessionEntity, Long> {

    Optional<WorkSessionEntity> findByUserIdAndStatus(Long userId, String status);
    Optional<WorkSessionEntity> findByUserIdAndStatusIn(Long userId, List<String> statuses);
}
