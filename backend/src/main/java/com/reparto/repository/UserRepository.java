package com.reparto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.reparto.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}