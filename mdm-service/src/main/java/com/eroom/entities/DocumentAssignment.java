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
@Table(name = "document_assignment")
public class DocumentAssignment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_type_id")
    private Long documentTypeId;

    @Column(name = "document_name")
    private String documentName;

    @OneToMany(targetEntity = DocumentAssignmentMappingWithSupplyVertical.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<DocumentAssignmentMappingWithSupplyVertical> documentAssignmentMappingWithSupplyVerticals;


    @OneToMany(targetEntity = DocumentAssignmentMappingWithDivision.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<DocumentAssignmentMappingWithDivision> documentAssignmentMappingWithDivisions;

    @OneToMany(targetEntity = FunctionalUserMappingWithDocAssigment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<FunctionalUserMappingWithDocAssigment> functionalUserMappingWithDocAssigment;

    @OneToMany(targetEntity = CheckerMappingWithDocAssigment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<CheckerMappingWithDocAssigment> checkerMappingWithDocAssignment;

    @OneToMany(targetEntity = FinalApproverMappingWithDocAssigment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<FinalApproverMappingWithDocAssigment> finalApproverMappingWithDocAssigment;

    @OneToMany(targetEntity = ProjectManagerMappingWithDocAssigment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<ProjectManagerMappingWithDocAssigment> projectManagerMappingWithDocAssigment;

    @OneToMany(targetEntity = DocumentAssignmentMappingWithDepartment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_assignment_id", referencedColumnName = "id")
    private Set<DocumentAssignmentMappingWithDepartment> departmentMappingWithDocAssigment;
}