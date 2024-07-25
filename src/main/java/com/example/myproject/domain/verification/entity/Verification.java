package com.example.myproject.domain.verification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long id;

    private Long userId;

    private String verificationCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Builder
    public Verification(Long userId, String verificationCode, Date expiryDate) {
        this.userId = userId;
        this.verificationCode = verificationCode;
        this.expiryDate = expiryDate;
    }
}
