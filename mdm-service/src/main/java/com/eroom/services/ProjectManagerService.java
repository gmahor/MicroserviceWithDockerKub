package com.eroom.services;

import com.eroom.dtos.DetailsOfDocumentDTO;

import java.util.List;

public interface ProjectManagerService {
	List<DetailsOfDocumentDTO> getDetailsOfDocument(String orderNumber, String customerName, String customerPo,
			String itemCode, String customerCode, String certificateNo, String fmSerialNo, String heatNo,
			String serialNo);

}
