package com.example.test.pismo.domain.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name="balance")
    private Double balance;

    public TransactionEntity() {
    }

    private Integer operationTypeId;

    public TransactionEntity(AccountEntity account, Double amount, Integer operationTypeId, Double balance) {
        this.accountEntity = account;
        this.amount = amount;
        this.operationTypeId = operationTypeId;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return accountEntity;
    }

    public void setAccountId(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Integer operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance){
        this.balance = balance;
    }
}
