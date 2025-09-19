package com.example.share.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto() {
    }

    public UserDto(String email, Long id, String name) {
        this.email = email;
        this.id = id;
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

