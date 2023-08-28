package com.card.task.app.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum RoleEnum {
    ROLE_USER("user"), ROLE_ADMIN("admin");

    private String name;

    private RoleEnum(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonCreator
    public static RoleEnum getRoleEnum(String value){

        return Stream.of(RoleEnum.values()).filter(direction -> direction.name.equals(value)).findFirst().orElse(null);
    }
}
