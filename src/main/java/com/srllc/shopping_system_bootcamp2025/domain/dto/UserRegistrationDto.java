package com.srllc.shopping_system_bootcamp2025.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

}
