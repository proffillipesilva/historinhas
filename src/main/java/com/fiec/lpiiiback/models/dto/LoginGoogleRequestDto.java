package com.fiec.lpiiiback.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginGoogleRequestDto {
    @NotBlank
    private String tokenId;
    @NotBlank
    private String clientId;
    private boolean reviewer;
}
