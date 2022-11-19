package com.fiec.lpiiiback.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {
    String email;
    String password;
    String cpf;
}
