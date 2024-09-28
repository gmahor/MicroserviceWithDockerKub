package com.eroom.enums;

import java.util.Arrays;

public enum RoleType {

    Admin("Admin"),

    Approver("Approver"),

    Checker("Checker"),

    Project_Manager("Project Manager"),

    Functional_User("Functional User");

    public final String value;

    RoleType(String value) {
        this.value = value;
    }


    public static RoleType getRoleType(String value) {
        return Arrays.stream(RoleType.values()).filter(s -> s.name().equals(value)).findFirst().orElse(null);
    }
}
