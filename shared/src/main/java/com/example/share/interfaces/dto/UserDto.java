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

    public UserDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User:\n" +
                "ID= " + id +
                ",\n Name= " + name +
                ",\n Email= " + email;
    }
}

