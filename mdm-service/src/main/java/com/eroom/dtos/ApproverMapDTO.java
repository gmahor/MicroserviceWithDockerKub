package com.eroom.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ApproverMapDTO {

    @NotEmpty(message = "approver  is required")
    private String approver;

    private Long approverId;

}
