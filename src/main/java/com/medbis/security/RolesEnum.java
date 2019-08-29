package com.medbis.security;

import java.util.ArrayList;

public enum RolesEnum {
    ROLE_NURSE,
    ROLE_ADMIN;

    public static ArrayList<String> getDefinedRoles(){
        return new ArrayList<String>(){{
           add(ROLE_NURSE.name());
            add(ROLE_ADMIN.name());
        }};
    }
}
