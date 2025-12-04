package com.example.test.pismo.application.repository;

import com.example.test.pismo.domain.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByDocumentNumber(String documentNumber);
}
