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

    @Override
    public String toString() {
        return "User:\n" +
                "ID= " + id +
                ",\n Name= " + name +
                ",\n Email= " + email;
    }
}

