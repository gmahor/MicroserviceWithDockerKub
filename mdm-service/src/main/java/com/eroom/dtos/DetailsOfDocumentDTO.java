package com.eroom.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailsOfDocumentDTO {

    private String level;

    private String version;

    private String docNo;

    private String itemCode;

    private String documentName;

    private String poNumber;

    private String customerName;

    private String docIndex;

    private String orderNumber;

    private String oaLineNo;

    private LocalDateTime modifiedOn;

    public DetailsOfDocumentDTO(String customerName, String poNumber, String documentName, String itemCode,
                                String docNo, String version, LocalDateTime modifiedOn, String level, String docIndex, String orderNumber,
                                String oaLineNo) {
        this.customerName = customerName;
        this.poNumber = poNumber;
        this.documentName = documentName;
        this.itemCode = itemCode;
        this.docNo = docNo;
        this.version = version;
        this.modifiedOn = modifiedOn;
        this.level = level;
        this.oaLineNo = oaLineNo;
        this.orderNumber = orderNumber;
        this.docIndex = docIndex;
    }
}
