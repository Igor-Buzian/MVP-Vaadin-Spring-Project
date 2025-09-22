package com.example.share.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;

    public UserDto() {
    }

    @Override
    public String toString() {
        return "User:\n" +
                "ID= " + id +
                ",\n Name= " + name +
                ",\n Email= " + email;
    }
}

