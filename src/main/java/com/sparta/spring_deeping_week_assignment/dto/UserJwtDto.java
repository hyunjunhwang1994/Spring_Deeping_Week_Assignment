package com.sparta.spring_deeping_week_assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJwtDto {

    private String username;
    private String jwt;


    public UserJwtDto(String name, String jwt) {
        this.username = name;
        this.jwt = jwt;
    }
}
