package com.example.pismo.infrastructure.repository;

import com.example.pismo.domain.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    @Query(value = "select * from transactions t where account_id = ? and balance < 0 order by event_date", nativeQuery = true)
    List<TransactionEntity> findNegativeTransactions(Integer accountId);
}
