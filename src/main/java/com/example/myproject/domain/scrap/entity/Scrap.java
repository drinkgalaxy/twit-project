package com.example.myproject.domain.scrap.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comment_id")
    private Long id;
}
