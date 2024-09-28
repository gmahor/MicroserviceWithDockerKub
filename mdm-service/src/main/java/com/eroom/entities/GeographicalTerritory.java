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
@Table(name = "geographical_territory")
public class GeographicalTerritory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geography_code")
    private String geographyCode;

    @Column(name = "geography_desc")
    private String geographyDesc;

    @Column(name = "status")
    private String status = "1";

}
