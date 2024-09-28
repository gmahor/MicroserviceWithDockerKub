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
@Table(name = "employee_master")
public class Employee extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empId", columnDefinition = "TEXT")
    private String empId;

    @Column(name = "employee_number", columnDefinition = "TEXT")
    private String employeeNumber;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "email")
    private String email;

    @Column(name = "cost_code", columnDefinition = "TEXT")
    private String costCode;

    @Column(name = "segment3", columnDefinition = "TEXT")
    private String segment3;

    @Column(name = "segment4", columnDefinition = "TEXT")
    private String segment4;

}