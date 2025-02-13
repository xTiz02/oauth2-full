package org.prd.users.util;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String roleName;

    RoleEnum(String roleName){
        this.roleName = roleName;
    }
}