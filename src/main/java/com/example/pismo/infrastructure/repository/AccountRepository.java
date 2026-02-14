package com.example.pismo.infrastructure.repository;

import com.example.pismo.domain.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByDocumentNumber(String documentNumber);

    @Transactional
    void deleteByDocumentNumber(String documentNumber);
}
