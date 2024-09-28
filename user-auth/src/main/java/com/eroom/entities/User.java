package com.eroom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_master_id")
    private Long employeeMasterId;

    @OneToMany(targetEntity = RoleMapping.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<RoleMapping> roles;

    @OneToMany(targetEntity = UserMappingWithDepartment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<UserMappingWithDepartment> userMappingWithDepartment;

    @OneToMany(targetEntity = UserMappingWithDivision.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<UserMappingWithDivision> userMappingWithDivision;

    @OneToMany(targetEntity = UserMappingWithGeography.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<UserMappingWithGeography> userMappingWithGeography;

    @OneToMany(targetEntity = UserMappingWithSupplyVertical.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<UserMappingWithSupplyVertical> userMappingWithSupplyVertical;

}
