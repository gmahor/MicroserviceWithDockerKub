package com.eroom.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "document_type")
public class DocumentType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = DocumentTypeMappingWithSupplyVertical.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private Set<DocumentTypeMappingWithSupplyVertical> documentTypeMappingWithSupplyVerticals;

    @OneToMany(targetEntity = DocumentTypeMappingWithDivision.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private Set<DocumentTypeMappingWithDivision> documentTypeMappingWithDivision;

    @OneToMany(targetEntity = DocumentTypeMappingWithOrganisation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private Set<DocumentTypeMappingWithOrganisation> documentTypeMappingWithOrganisation;

    @OneToMany(targetEntity = DocumentTypeMappingWithDepartment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private Set<DocumentTypeMappingWithDepartment> documentTypeMappingWithDepartment;

    @OneToMany(targetEntity = DocumentTypeMappingWithGeography.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", referencedColumnName = "id")
    private Set<DocumentTypeMappingWithGeography> documentTypeMappingWithGeographies;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_abbreviation")
    private String documentAbbreviation;

    @Column(name = "level")
    private String level;

}