package com.dailycodework.buynowdotcom.dtos;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // Optional: you might want to exclude this for security
    private String phone;
}
