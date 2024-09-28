package com.eroom.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRespondDTO {

    private String department;

    private String supplyVertical;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String employeeId;

    private String geography;

    private String division;

    private String middleName;

    private String roleName;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private String createdBy;

    private String modifiedBy;

}
