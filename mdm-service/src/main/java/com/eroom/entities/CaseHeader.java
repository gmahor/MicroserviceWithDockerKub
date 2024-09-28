//package com.eroom.entities;
//
//import com.eroom.enums.CaseType;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "case_header")
//public class CaseHeader {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "po_number")
//    private String poNumber;
//
//    @Column(name = "fm_order_no")
//    private String fmOrderNo;
//
//    @Column(length = 1000, name = "customer_name")
//    private String customerName;
//
//    @Column(name = "supply_vertical")
//    private String supplyVertical;
//
//    @Column(name = "supply_vertical_id")
//    private Long supplyVerticalId;
//
//    @Column(name = "oa_booked_date")
//    private LocalDateTime oaBookedDate;
//
//    @Column(name = "oa_status")
//    private String oaStatus;
//
//    @Column(name = "rd")
//    private Date rd;
//
//    @Column(name = "ssd")
//    private Date ssd;
//
//    @Column(name = "e_room_id")
//    private String eRoomId;
//
//    @Enumerated(EnumType.STRING)
//    private CaseType caseType;
//
//    @Column(name = "org_name")
//    private String orgName;
//
//    @Column(name = "org_id")
//    private Long orgId;
//
//    @Column(name = "div_code")
//    private String divCode;
//
//    @Column(name = "division")
//    private String division;
//
//    @Column(name = "div_id")
//    private Long divId;
//
//    @Column(name = "oef_no")
//    private String oefNo;
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
//}