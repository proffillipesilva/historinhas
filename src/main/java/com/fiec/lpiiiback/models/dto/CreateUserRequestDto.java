package com.fiec.lpiiiback.models.dto;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    String name;
    String lastName;
    String email;
    String password;
    String phoneNumber;
    String cpf;
    String age;

}
