package com.fiec.lpiiiback.models.dto;

import lombok.Data;

@Data
public class LoginGoogleRequestDto {
    private String tokenId;
    private boolean reviewer;
}
