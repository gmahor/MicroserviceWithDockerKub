//package com.eroom.entities;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "case_lineitem")
//public class CaseLineItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "case_id")
//    private Long caseId;
//
//    @Column(name = "org_id")
//    private Long orgId;
//
//    @Column(name = "org_name")
//    private String orgName;
//
//    @Column(name = "supply_vertical")
//    private String supplyVertical;
//
//    @Column(name = "oa_line_no")
//    private String oaLineNo;
//
//    @Column(name = "item_code")
//    private String itemCode;
//
//    @Column(name = "document_name")
//    private String documentName;
//
//    @Column(name = "document_type")
//    private String documentType;
//
//    @Column(name = "level")
//    private String level;
//
//    @Column(name = "doc_update_date")
//    private String docUpdateDate;
//
//    @Column(name = "completion_status")
//    private String completionStatus;
//
//    @Column(name = "version")
//    private String version;
//
//    @Column(name = "division")
//    private String division;
//
//    @Column(name = "document_type_id")
//    private Long documentTypeId;
//
//    @Column(name = "doc_no")
//    private String docNo;
//
//    @Column(name = "order_number")
//    private String orderNumber;
//
//    private String documentAbbreviation;
//
//    private String docIndex;
//
//    @Column(name = "foreign_key")
//    private Long foreignKey;
//
//    @CreationTimestamp
//    @Column(name = "created_on", updatable = false)
//    private LocalDateTime createdOn;
//
//    @UpdateTimestamp
//    @Column(name = "modified_on")
//    private LocalDateTime modifiedOn;
//
//    @Column(name = "status")
//    private String status = "1";
//
//    private String pendingAtLevel;
//}
//
