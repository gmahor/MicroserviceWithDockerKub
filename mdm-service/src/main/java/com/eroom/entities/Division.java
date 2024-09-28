package com.eroom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "division")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "division_code", columnDefinition = "TEXT")
    private String divisionCode;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "division_id")
    private Long divisionId;

    @Column(name = "parent_code", columnDefinition = "TEXT")
    private String parentCode;

    @Column(name = "status")
    private String status = "1";

}