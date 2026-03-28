package com.reparto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reparto.entity.ClientEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

}
