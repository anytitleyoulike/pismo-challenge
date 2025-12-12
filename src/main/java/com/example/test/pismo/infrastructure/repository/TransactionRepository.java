package com.example.test.pismo.infrastructure.repository;

import com.example.test.pismo.domain.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    @Query(value = "select * from transactions t where account_id = ? and balance < 0 order by event_date ", nativeQuery = true)
    List<TransactionEntity> findNegativeTransactions(Integer accountId);
}
