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
@Table(name = "cost_centre_master")
public class CostCentreMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_code", columnDefinition = "TEXT")
    private String parentCode;

    @Column(name = "natural_acc_name", columnDefinition = "TEXT")
    private String naturalAccName;

    @Column(name = "natural_acc_id")
    private Double naturalAccId;

    @Column(name = "child_desc", columnDefinition = "TEXT")
    private String childDesc;

    @Column(name = "child_code", columnDefinition = "TEXT")
    private String childCode;

}
