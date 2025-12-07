package com.example.test.pismo.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

@Entity
@Table(name ="accounts")
public class AccountEntity {
    @PersistenceCreator
    public AccountEntity(){}

    @Id
    @Column(name = "account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "document_number", unique = true, nullable = false, length = 11)
    private String documentNumber;

    public AccountEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public AccountEntity(Integer id, String documentNumber) {
        this.id = id;
        this.documentNumber = documentNumber;
    }

    public Integer getId() {
        return id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }
}
